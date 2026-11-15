package org.pa2.cardenas.cardenas_pa2;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

/**
 * Formulario para crear (registrar) un paciente nuevo en SQLite.
 */
public class RegistrarActivity extends AppCompatActivity {

    private TextInputEditText etDni, etNombre, etEdad, etDiagnostico, etTratamiento;
    private PacienteDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = new PacienteDbHelper(this);

        etDni = findViewById(R.id.etDni);
        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        etDiagnostico = findViewById(R.id.etDiagnostico);
        etTratamiento = findViewById(R.id.etTratamiento);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnLimpiar = findViewById(R.id.btnLimpiar);

        btnGuardar.setOnClickListener(v -> guardarPaciente());
        btnLimpiar.setOnClickListener(v -> limpiarCampos());
    }

    /** Valida los datos del formulario y los guarda en la base de datos. */
    private void guardarPaciente() {
        String dni = textoDe(etDni);
        String nombre = textoDe(etNombre);
        String edadTexto = textoDe(etEdad);
        String diagnostico = textoDe(etDiagnostico);
        String tratamiento = textoDe(etTratamiento);

        if (!dni.matches("\\d{8}")) {
            etDni.setError("El DNI debe tener 8 dígitos");
            return;
        }
        if (TextUtils.isEmpty(nombre)) {
            etNombre.setError("Ingrese el nombre");
            return;
        }
        if (TextUtils.isEmpty(edadTexto)) {
            etEdad.setError("Ingrese la edad");
            return;
        }
        int edad = Integer.parseInt(edadTexto);
        if (edad < 0 || edad > 120) {
            etEdad.setError("Edad no válida (0 - 120)");
            return;
        }
        if (TextUtils.isEmpty(diagnostico)) {
            etDiagnostico.setError("Ingrese el diagnóstico");
            return;
        }
        if (TextUtils.isEmpty(tratamiento)) {
            etTratamiento.setError("Ingrese el tratamiento");
            return;
        }

        long idGenerado = dbHelper.insertarPaciente(dni, nombre, edad, diagnostico, tratamiento);
        if (idGenerado == -1) {
            etDni.setError("Ya existe un paciente con ese DNI");
            Toast.makeText(this, "No se pudo registrar: DNI repetido", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Paciente registrado correctamente", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        etDni.setText("");
        etNombre.setText("");
        etEdad.setText("");
        etDiagnostico.setText("");
        etTratamiento.setText("");
        etDni.requestFocus();
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
