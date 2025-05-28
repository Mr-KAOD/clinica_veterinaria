package com.veterinaria;

public class Gato extends Mascota{
    private boolean esterilizado;

    public Gato(String nombre, String raza, int edad, String genero, double peso,
                double altura, Propietario propietario, boolean esterilizado) {
        super(nombre, "GATO", raza, edad, genero, peso, altura, propietario);
        this.esterilizado = esterilizado;
    }

    @Override
    public String toString() {
        return "Gato [nombre=" + nombre + ", especie=" + especie + ", raza=" + raza + ", edad=" + edad + ", genero="
                + genero + ", peso=" + peso + ", altura=" + altura + ", propietario=" + propietario.toString() + "]";
    }
}