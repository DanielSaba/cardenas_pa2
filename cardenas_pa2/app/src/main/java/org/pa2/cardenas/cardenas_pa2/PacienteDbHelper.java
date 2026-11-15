package org.pa2.cardenas.cardenas_pa2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Administra la base de datos SQLite del consultorio.
 * Aquí se crean la base y la tabla, y se implementan las operaciones
 * CRUD: crear, buscar, actualizar, listar y eliminar pacientes.
 */
public class PacienteDbHelper extends SQLiteOpenHelper {

    public static final String NOMBRE_BD = "consultorio_cardenas.db";
    public static final int VERSION_BD = 1;

    public static final String TABLA_PACIENTES = "pacientes";
    public static final String COL_ID = "id";
    public static final String COL_DNI = "dni";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_EDAD = "edad";
    public static final String COL_DIAGNOSTICO = "diagnostico";
    public static final String COL_TRATAMIENTO = "tratamiento";
    public static final String COL_FECHA_REGISTRO = "fecha_registro";

    public PacienteDbHelper(Context contexto) {
        super(contexto, NOMBRE_BD, null, VERSION_BD);
    }

    /** Se ejecuta la primera vez que se crea la base de datos. */
    @Override
    public void onCreate(SQLiteDatabase bd) {
        String sqlCrearTabla = "CREATE TABLE " + TABLA_PACIENTES + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_DNI + " TEXT NOT NULL UNIQUE, "
                + COL_NOMBRE + " TEXT NOT NULL, "
                + COL_EDAD + " INTEGER NOT NULL, "
                + COL_DIAGNOSTICO + " TEXT NOT NULL, "
                + COL_TRATAMIENTO + " TEXT NOT NULL, "
                + COL_FECHA_REGISTRO + " TEXT NOT NULL)";
        bd.execSQL(sqlCrearTabla);
    }

    /** Se ejecuta cuando cambia VERSION_BD: recrea la tabla. */
    @Override
    public void onUpgrade(SQLiteDatabase bd, int versionAnterior, int versionNueva) {
        bd.execSQL("DROP TABLE IF EXISTS " + TABLA_PACIENTES);
        onCreate(bd);
    }

    /**
     * CREAR: inserta un paciente nuevo.
     * @return el id generado, o -1 si falló (por ejemplo, DNI repetido).
     */
    public long insertarPaciente(String dni, String nombre, int edad,
                                 String diagnostico, String tratamiento) {
        SQLiteDatabase bd = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COL_DNI, dni);
        valores.put(COL_NOMBRE, nombre);
        valores.put(COL_EDAD, edad);
        valores.put(COL_DIAGNOSTICO, diagnostico);
        valores.put(COL_TRATAMIENTO, tratamiento);
        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(new Date());
        valores.put(COL_FECHA_REGISTRO, fecha);
        return bd.insert(TABLA_PACIENTES, null, valores);
    }

    /**
     * BUSCAR: obtiene un paciente por su DNI (campo clave).
     * @return el paciente, o null si no existe.
     */
    public Paciente buscarPacientePorDni(String dni) {
        SQLiteDatabase bd = getReadableDatabase();
        Cursor cursor = bd.query(TABLA_PACIENTES, null,
                COL_DNI + " = ?", new String[]{dni}, null, null, null);
        Paciente paciente = null;
        if (cursor.moveToFirst()) {
            paciente = convertirCursorAPaciente(cursor);
        }
        cursor.close();
        return paciente;
    }

    /**
     * MODIFICAR: actualiza los datos de un paciente existente.
     * @return la cantidad de filas actualizadas (1 si salió bien).
     */
    public int actualizarPaciente(Paciente paciente) {
        SQLiteDatabase bd = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COL_NOMBRE, paciente.getNombre());
        valores.put(COL_EDAD, paciente.getEdad());
        valores.put(COL_DIAGNOSTICO, paciente.getDiagnostico());
        valores.put(COL_TRATAMIENTO, paciente.getTratamiento());
        return bd.update(TABLA_PACIENTES, valores,
                COL_ID + " = ?", new String[]{String.valueOf(paciente.getId())});
    }

    /**
     * LISTAR: devuelve todos los pacientes ordenados alfabéticamente por nombre.
     */
    public List<Paciente> listarPacientes() {
        List<Paciente> listaPacientes = new ArrayList<>();
        SQLiteDatabase bd = getReadableDatabase();
        Cursor cursor = bd.query(TABLA_PACIENTES, null, null, null, null, null,
                COL_NOMBRE + " COLLATE NOCASE ASC");
        while (cursor.moveToNext()) {
            listaPacientes.add(convertirCursorAPaciente(cursor));
        }
        cursor.close();
        return listaPacientes;
    }

    /**
     * ELIMINAR: borra un paciente por su id.
     * @return la cantidad de filas eliminadas (1 si salió bien).
     */
    public int eliminarPaciente(int idPaciente) {
        SQLiteDatabase bd = getWritableDatabase();
        return bd.delete(TABLA_PACIENTES, COL_ID + " = ?",
                new String[]{String.valueOf(idPaciente)});
    }

    /** Convierte la fila actual del cursor en un objeto Paciente. */
    private Paciente convertirCursorAPaciente(Cursor cursor) {
        return new Paciente(
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_DNI)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_EDAD)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_DIAGNOSTICO)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_TRATAMIENTO)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_FECHA_REGISTRO)));
    }
}
