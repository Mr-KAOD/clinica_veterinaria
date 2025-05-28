package com.veterinaria;

public class Perro extends Mascota{
    private String fechaUltimaVacunacion;

    public Perro(String nombre, String raza, int edad, String genero, double peso,
                 double altura, Propietario propietario, String fechaUltimaVacunacion) {
        super(nombre, "PERRO", raza, edad, genero, peso, altura, propietario);
        this.fechaUltimaVacunacion = fechaUltimaVacunacion;
    }

    @Override
    public String toString() {
        return "Perro [nombre=" + nombre + ", fechaUltimaVacunacion=" + fechaUltimaVacunacion + ", especie=" + especie
                + ", raza=" + raza + ", edad=" + edad + ", genero=" + genero + ", peso=" + peso + ", altura=" + altura
                + ", propietario=" + propietario + "]";
    }
}
