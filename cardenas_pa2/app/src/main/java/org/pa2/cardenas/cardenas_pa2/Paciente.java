package org.pa2.cardenas.cardenas_pa2;

/**
 * Modelo de datos que representa a un paciente del consultorio.
 */
public class Paciente {

    private int id;
    private String dni;
    private String nombre;
    private int edad;
    private String diagnostico;
    private String tratamiento;
    private String fechaRegistro;

    public Paciente(int id, String dni, String nombre, int edad,
                    String diagnostico, String tratamiento, String fechaRegistro) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.fechaRegistro = fechaRegistro;
    }

    public int getId() { return id; }
    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getDiagnostico() { return diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public String getFechaRegistro() { return fechaRegistro; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }
}
