package com.veterinaria;

public class Dosis {
    private String frecuencia; // cada 4h, 8h, etc.
    private String cantidad;   // 1 dosis, 1/2 dosis

    public Dosis(String frecuencia, String cantidad) {
        this.frecuencia = frecuencia;
        this.cantidad = cantidad;
    }
}
