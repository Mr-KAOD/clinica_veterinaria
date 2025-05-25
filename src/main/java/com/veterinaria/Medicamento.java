package com.veterinaria;

public class Medicamento {
    private String nombre;
    private double costo;

    public Medicamento(String nombre, double costo) {
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
        return "Medicamento [nombre=" + nombre + ", costo=" + costo + "]";
    }

}
