package com.atm.proj.classes;

import java.util.ArrayList;

public class Cont {
    private String name;
    private String id;
    private Utilizator proprietar;
    private ArrayList<Tranzactie> tranzactii;

    public Cont(String name, Utilizator proprietar, Banca banca) {
        this.name = name;
        this.proprietar = proprietar;

        //generare id cont
        this.id = banca.getIdNouCont();

        this.tranzactii = new ArrayList<Tranzactie>();
    }

    public String getId() {
        return this.id;
    }

    public String getTranzactie() {
        //afiseaza balanta
        double balanta = this.getBalanta();

        //vedem daca e negatuiv sau nu
        if(balanta >= 0 ){
            return String.format("%s : %.02f lei : %s", this.id, balanta, this.name);
        }
        else{
            return String.format("%s : (%.02f) lei : %s ", this.id, balanta, this.name);
        }
    }

    public double getBalanta() {
        double balanta = 0;
        for(Tranzactie tranzactie : this.tranzactii){
            balanta += tranzactie.getSuma();
        }
        return balanta;
    }

    public void afiseazaIstoricTranz() {
        System.out.printf("\n\nIstoric tranzactii pentru contul %s \n", this.getId());
        for(int i =  this.tranzactii.size() - 1; i >=0 ; i--) {
            System.out.printf(this.tranzactii.get(i).getLinieBalanta());
        }
        System.out.println();
    }

    public void addTranzactie(double suma, String descriere) {
        Tranzactie tranzactie = new Tranzactie(suma, descriere, this);
        this.tranzactii.add(tranzactie);
    }
}
