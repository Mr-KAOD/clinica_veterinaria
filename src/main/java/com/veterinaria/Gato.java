package com.veterinaria;

public class Gato extends Mascota{
    private boolean esterilizado;

    public Gato(String nombre, String raza, int edad, String genero, double peso,
                double altura, Propietario propietario, boolean esterilizado) {
        super(nombre, "Gato", raza, edad, genero, peso, altura, propietario);
        this.esterilizado = esterilizado;
    }
}