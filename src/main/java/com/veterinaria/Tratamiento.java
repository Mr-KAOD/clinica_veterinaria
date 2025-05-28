package com.veterinaria;

import java.util.ArrayList;
import java.util.List;

public class Tratamiento {
    private ArrayList<Medicamento> medicamentos;
    private ArrayList<Dosis> dosis;
    private int duracionDias;

    public Tratamiento() {
        medicamentos = new ArrayList<>();
        dosis = new ArrayList<>();
    }

    public void agregarMedicamento(Medicamento medicamento, Dosis dosis) {
        medicamentos.add(medicamento);
        this.dosis.add(dosis);
    }

    public ArrayList<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(ArrayList<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public ArrayList<Dosis> getDosis() {
        return dosis;
    }

    public void setDosis(ArrayList<Dosis> dosis) {
        this.dosis = dosis;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }


}
