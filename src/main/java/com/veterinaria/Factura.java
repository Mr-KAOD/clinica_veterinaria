package com.veterinaria;

public class Factura {
    private Consulta consulta;

    public Factura(Consulta consulta) {
        this.consulta = consulta;
    }

    public double getTotal() {
        return consulta.calcularCostoTotal();
    }

    @Override
    public String toString() {
        return "Factura consulta=" + consulta + "]";
    }
}
