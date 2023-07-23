package com.atm.proj.classes;

import java.util.Date;

public class Tranzactie {
    private double suma;
    private Date data;
    private String descriere;
    private Cont contulTranzactiei;

    public Tranzactie(double suma, Cont contulTranzactiei){
        this.suma = suma;
        this.contulTranzactiei = contulTranzactiei;
        this.data = new Date();
        this.descriere = "";
    }

    public Tranzactie(double suma, String descriere, Cont contulTranzactiei){
        this(suma, contulTranzactiei);
        this.descriere = descriere;
    }

    public double getSuma() {
        return this.suma;
    }

    public String getLinieBalanta() {
        if(this.suma >= 0 ){
            return String.format("%s : %.02f lei : %s", this.data.toString(), this.suma, this.descriere);
        }
        else return String.format("%s : (%.02f) lei : %s", this.data.toString(), this.suma, this.descriere);

    }
}
