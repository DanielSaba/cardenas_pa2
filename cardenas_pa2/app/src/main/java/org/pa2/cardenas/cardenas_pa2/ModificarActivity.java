package org.pa2.cardenas.cardenas_pa2;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

/**
 * Busca un paciente por DNI, permite actualizar sus datos o eliminarlo.
 */
public class ModificarActivity extends AppCompatActivity {

    public static final String EXTRA_DNI = "dni_paciente";

    private TextInputEditText etBuscarDni, etNombre, etEdad, etDiagnostico, etTratamiento;
    private Button btnActualizar, btnEliminar;
    private PacienteDbHelper dbHelper;
    private Paciente pacienteActual; // paciente encontrado en la búsqueda

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = new PacienteDbHelper(this);

        etBuscarDni = findViewById(R.id.etBuscarDni);
        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        etDiagnostico = findViewById(R.id.etDiagnostico);
        etTratamiento = findViewById(R.id.etTratamiento);
        Button btnBuscar = findViewById(R.id.btnBuscar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        habilitarEdicion(false);

        btnBuscar.setOnClickListener(v -> buscarPaciente());
        btnActualizar.setOnClickListener(v -> actualizarPaciente());
        btnEliminar.setOnClickListener(v -> confirmarEliminacion());

        // Si llegamos desde la lista, se busca automáticamente el DNI recibido
        String dniRecibido = getIntent().getStringExtra(EXTRA_DNI);
        if (dniRecibido != null) {
            etBuscarDni.setText(dniRecibido);
            buscarPaciente();
        }
    }

    /** Busca el paciente por DNI y muestra sus datos en el formulario. */
    private void buscarPaciente() {
        String dni = textoDe(etBuscarDni);
        if (TextUtils.isEmpty(dni)) {
            etBuscarDni.setError("Ingrese un DNI");
            return;
        }
        pacienteActual = dbHelper.buscarPacientePorDni(dni);
        if (pacienteActual == null) {
            habilitarEdicion(false);
            limpiarCampos();
            Toast.makeText(this, "No se encontró un paciente con ese DNI", Toast.LENGTH_LONG).show();
            return;
        }
        etNombre.setText(pacienteActual.getNombre());
        etEdad.setText(String.valueOf(pacienteActual.getEdad()));
        etDiagnostico.setText(pacienteActual.getDiagnostico());
        etTratamiento.setText(pacienteActual.getTratamiento());
        habilitarEdicion(true);
        Toast.makeText(this, "Paciente encontrado", Toast.LENGTH_SHORT).show();
    }

    /** Guarda en SQLite los cambios hechos al paciente encontrado. */
    private void actualizarPaciente() {
        if (pacienteActual == null) return;

        String nombre = textoDe(etNombre);
        String edadTexto = textoDe(etEdad);
        String diagnostico = textoDe(etDiagnostico);
        String tratamiento = textoDe(etTratamiento);

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(edadTexto)
                || TextUtils.isEmpty(diagnostico) || TextUtils.isEmpty(tratamiento)) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        int edad = Integer.parseInt(edadTexto);
        if (edad < 0 || edad > 120) {
            etEdad.setError("Edad no válida (0 - 120)");
            return;
        }

        pacienteActual.setNombre(nombre);
        pacienteActual.setEdad(edad);
        pacienteActual.setDiagnostico(diagnostico);
        pacienteActual.setTratamiento(tratamiento);

        int filasActualizadas = dbHelper.actualizarPaciente(pacienteActual);
        if (filasActualizadas > 0) {
            Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se pudo actualizar", Toast.LENGTH_LONG).show();
        }
    }

    /** Pide confirmación antes de eliminar el registro. */
    private void confirmarEliminacion() {
        if (pacienteActual == null) return;
        new AlertDialog.Builder(this)
                .setTitle("Eliminar paciente")
                .setMessage("¿Seguro que desea eliminar a " + pacienteActual.getNombre() + "?")
                .setPositiveButton("Eliminar", (dialogo, which) -> eliminarPaciente())
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void eliminarPaciente() {
        int filasEliminadas = dbHelper.eliminarPaciente(pacienteActual.getId());
        if (filasEliminadas > 0) {
            Toast.makeText(this, "Paciente eliminado", Toast.LENGTH_SHORT).show();
            pacienteActual = null;
            limpiarCampos();
            etBuscarDni.setText("");
            habilitarEdicion(false);
        }
    }

    private void habilitarEdicion(boolean habilitado) {
        etNombre.setEnabled(habilitado);
        etEdad.setEnabled(habilitado);
        etDiagnostico.setEnabled(habilitado);
        etTratamiento.setEnabled(habilitado);
        btnActualizar.setEnabled(habilitado);
        btnEliminar.setEnabled(habilitado);
    }

    private void limpiarCampos() {
        etNombre.setText("");
        etEdad.setText("");
        etDiagnostico.setText("");
        etTratamiento.setText("");
    }

    private String textoDe(TextInputEditText campo) {
        return campo.getText() == null ? "" : campo.getText().toString().trim();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
