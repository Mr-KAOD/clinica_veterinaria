package com.veterinaria;

import java.util.ArrayList;
import java.util.List;

public class Tratamiento {
    private List<Medicamento> medicamentos;
    private List<Dosis> dosis;
    private int duracionDias;

    public Tratamiento() {
        medicamentos = new ArrayList<>();
        dosis = new ArrayList<>();
    }

    public void agregarMedicamento(Medicamento medicamento, Dosis dosis) {
        medicamentos.add(medicamento);
        this.dosis.add(dosis);
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }
}
