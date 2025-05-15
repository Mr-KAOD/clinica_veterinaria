package com.veterinaria;

public class Propietario {
    private String id;
    private String nombre;
    private String direccion;
    private String correo;
    private String telefono;

    public Propietario(String id, String nombre, String direccion, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getId() {
        return id;
    }
}
