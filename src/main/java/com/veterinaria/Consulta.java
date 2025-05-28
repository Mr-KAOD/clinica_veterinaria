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

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public List<Examen> getExamenes() {
        return examenes;
    }

    public void setExamenes(List<Examen> examenes) {
        this.examenes = examenes;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
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
<<<<<<< HEAD
=======

    @Override
    public String toString() {
        return "Consulta -> veterinario=" + veterinario + ", mascota=" + mascota.toString() + ", diagnostico=" + diagnostico
                + ", examenes=" + examenes + ", tratamiento=" + tratamiento + ", fecha=" + fecha;
    }
>>>>>>> main
}