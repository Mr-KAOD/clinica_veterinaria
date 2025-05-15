package com.veterinaria;

public class Examen {
    private String nombre;
    private double costo;

    public Examen(String nombre, double costo) {
        this.nombre = nombre;
        this.costo = costo;
    }

    public double getCosto() {
        return costo;
    }
}
