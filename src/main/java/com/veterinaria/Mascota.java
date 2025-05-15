package com.veterinaria;

public class Mascota {
    protected String nombre;
    protected String especie;
    protected String raza;
    protected int edad;
    protected String genero;
    protected double peso;
    protected double altura;
    protected Propietario propietario;
    //protected HistorialMedico historialMedico;

    public Mascota(String nombre, String especie, String raza, int edad, String genero,
                   double peso, double altura, Propietario propietario) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.genero = genero;
        this.peso = peso;
        this.altura = altura;
        this.propietario = propietario;
    }

    public String getNombre() {
        return nombre;
    }
}
