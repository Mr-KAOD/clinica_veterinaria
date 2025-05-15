package com.veterinaria;

import java.util.ArrayList;
import java.util.List;

public class HistorialMedico {
    private Mascota mascota;
    private List<Consulta> consultas;

    public HistorialMedico(Mascota mascota) {
        this.mascota = mascota;
        this.consultas = new ArrayList<>();
    }

    public void agregarConsulta(Consulta c) {
        consultas.add(c);
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }
}
