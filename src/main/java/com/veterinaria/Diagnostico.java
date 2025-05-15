package com.veterinaria;

import java.util.Date;

public class Diagnostico {  
    private String concepto;
    private Date fecha;

    public Diagnostico(String concepto, Date fecha) {
        this.concepto = concepto;
        this.fecha = fecha;
    }    
}
