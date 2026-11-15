package org.pa2.cardenas.cardenas_pa2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adaptador que muestra cada paciente en una tarjeta dentro del RecyclerView.
 */
public class PacienteAdapter extends RecyclerView.Adapter<PacienteAdapter.PacienteViewHolder> {

    /** Permite avisar a la pantalla cuando se toca un paciente. */
    public interface OnPacienteClickListener {
        void onPacienteClick(Paciente paciente);
    }

    private final List<Paciente> listaPacientes;
    private final OnPacienteClickListener listener;

    public PacienteAdapter(List<Paciente> listaPacientes, OnPacienteClickListener listener) {
        this.listaPacientes = listaPacientes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PacienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_paciente, parent, false);
        return new PacienteViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PacienteViewHolder holder, int posicion) {
        Paciente paciente = listaPacientes.get(posicion);
        holder.txtNombre.setText(paciente.getNombre());
        holder.txtDniEdad.setText("DNI: " + paciente.getDni() + "  |  " + paciente.getEdad() + " años");
        holder.txtDiagnostico.setText("Diagnóstico: " + paciente.getDiagnostico());
        holder.txtTratamiento.setText("Tratamiento: " + paciente.getTratamiento());
        holder.txtFecha.setText("Registrado: " + paciente.getFechaRegistro());
        holder.itemView.setOnClickListener(v -> listener.onPacienteClick(paciente));
    }

    @Override
    public int getItemCount() {
        return listaPacientes.size();
    }

    static class PacienteViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtDniEdad, txtDiagnostico, txtTratamiento, txtFecha;

        PacienteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtDniEdad = itemView.findViewById(R.id.txtDniEdad);
            txtDiagnostico = itemView.findViewById(R.id.txtDiagnostico);
            txtTratamiento = itemView.findViewById(R.id.txtTratamiento);
            txtFecha = itemView.findViewById(R.id.txtFecha);
        }
    }
}
