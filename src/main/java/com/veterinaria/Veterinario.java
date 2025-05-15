package com.veterinaria;

public class Veterinario {
    private String nombre;
    private String especialidad;
    private String registro;

    public Veterinario(String nombre, String especialidad, String registro) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.registro = registro;
    }

    public String getNombre() {
        return nombre;
    }
}
