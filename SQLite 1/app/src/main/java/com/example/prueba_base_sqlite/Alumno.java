package com.example.prueba_base_sqlite;

public class Alumno {

    private String nombre, apellido, direccion, telefono;

    public Alumno(String nombre, String apellido, String direccion, String telefono){
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }


    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

}
