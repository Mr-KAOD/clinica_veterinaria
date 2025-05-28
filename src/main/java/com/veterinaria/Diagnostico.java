package com.veterinaria;

import java.util.Date;

public class Diagnostico {  
    private String concepto;
    private Date fecha;

    public Diagnostico(String concepto, Date fecha) {
        this.concepto = concepto;
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Diagnostico [concepto=" + concepto + ", fecha=" + fecha + "]";
    }    
}
