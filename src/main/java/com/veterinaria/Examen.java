package com.veterinaria;

public class Examen {
    private String nombre;
    private double costo;

    public Examen(String nombre, double costo) {
        this.nombre = nombre;
        this.costo = costo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "Examen [nombre=" + nombre + ", costo=" + costo + "]";
    }
}
