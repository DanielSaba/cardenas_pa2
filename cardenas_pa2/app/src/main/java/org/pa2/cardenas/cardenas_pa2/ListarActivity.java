package org.pa2.cardenas.cardenas_pa2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Muestra todos los pacientes guardados en SQLite usando un RecyclerView.
 */
public class ListarActivity extends AppCompatActivity {

    private RecyclerView recyclerPacientes;
    private TextView txtTotal, txtVacio;
    private PacienteDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = new PacienteDbHelper(this);
        recyclerPacientes = findViewById(R.id.recyclerPacientes);
        txtTotal = findViewById(R.id.txtTotal);
        txtVacio = findViewById(R.id.txtVacio);
        recyclerPacientes.setLayoutManager(new LinearLayoutManager(this));
    }

    /** Se recarga la lista cada vez que la pantalla vuelve a mostrarse. */
    @Override
    protected void onResume() {
        super.onResume();
        cargarLista();
    }

    private void cargarLista() {
        List<Paciente> listaPacientes = dbHelper.listarPacientes();
        txtTotal.setText("Total de pacientes: " + listaPacientes.size());

        if (listaPacientes.isEmpty()) {
            txtVacio.setVisibility(View.VISIBLE);
            recyclerPacientes.setVisibility(View.GONE);
        } else {
            txtVacio.setVisibility(View.GONE);
            recyclerPacientes.setVisibility(View.VISIBLE);
            // Al tocar un paciente se abre la pantalla de modificación con su DNI
            recyclerPacientes.setAdapter(new PacienteAdapter(listaPacientes, paciente -> {
                Intent intent = new Intent(this, ModificarActivity.class);
                intent.putExtra(ModificarActivity.EXTRA_DNI, paciente.getDni());
                startActivity(intent);
            }));
        }
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
