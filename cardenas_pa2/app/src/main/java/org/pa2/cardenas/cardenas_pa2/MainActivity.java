package org.pa2.cardenas.cardenas_pa2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Pantalla principal: menú con las opciones de la aplicación.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        Button btnModificar = findViewById(R.id.btnModificar);
        Button btnListar = findViewById(R.id.btnListar);
        Button btnSalir = findViewById(R.id.btnSalir);

        btnRegistrar.setOnClickListener(v ->
                startActivity(new Intent(this, RegistrarActivity.class)));
        btnModificar.setOnClickListener(v ->
                startActivity(new Intent(this, ModificarActivity.class)));
        btnListar.setOnClickListener(v ->
                startActivity(new Intent(this, ListarActivity.class)));

        // Cierra la aplicación por completo
        btnSalir.setOnClickListener(v -> finishAffinity());
    }
}
