package com.veterinaria;

public class Perro extends Mascota{
    private String fechaUltimaVacunacion;

    public Perro(String nombre, String raza, int edad, String genero, double peso,
                 double altura, Propietario propietario, String fechaUltimaVacunacion) {
        super(nombre, "Perro", raza, edad, genero, peso, altura, propietario);
        this.fechaUltimaVacunacion = fechaUltimaVacunacion;
    }
}
