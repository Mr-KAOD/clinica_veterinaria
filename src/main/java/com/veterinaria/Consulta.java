package com.veterinaria;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Consulta {
    private Veterinario veterinario;
    private Mascota mascota;
    private Diagnostico diagnostico;
    private List<Examen> examenes;
    private Tratamiento tratamiento;
    private Date fecha;
    private Factura factura;

    public Consulta(Veterinario veterinario, Mascota mascota, Date fecha) {
        this.veterinario = veterinario;
        this.mascota = mascota;
        this.fecha = fecha;
        this.examenes = new ArrayList<>();
    }

    public void agregarDiagnostico(Diagnostico d) {
        this.diagnostico = d;
    }

    public void agregarExamen(Examen e) {
        this.examenes.add(e);
    }

    public void agregarTratamiento(Tratamiento t) {
        this.tratamiento = t;
    }

    public double calcularCostoTotal() {
        double total = 20000; // valor por defecto
        if (tratamiento != null) {
            for (Medicamento m : tratamiento.getMedicamentos()) {
                total += m.getCosto();
            }
        }
        for (Examen e : examenes) {
            total += e.getCosto();
        }
        return total;
    }

    public Mascota getMascota() {
        return mascota;
    }
    public Veterinario getVeterinario() {
        return veterinario;
    }
    public Tratamiento getTratamiento() {
        return tratamiento;
    }
}