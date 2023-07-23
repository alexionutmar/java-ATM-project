package com.atm.proj.classes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Utilizator {
    private String nume;
    private String prenume;
    private String id;
    private byte pinHash[];
    private ArrayList<Cont> conturi;

    public Utilizator(String nume, String prenume, String pin, Banca banca) {
        this.nume = nume;
        this.prenume = prenume;

        //hash the pin
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            this.pinHash = messageDigest.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Eroare pin");
            e.printStackTrace();
            System.exit(1);
        }

        //generare id
        this.id = banca.getIdNouUtilizator();

        //lista empty de conturi
        this.conturi = new ArrayList<Cont>();

        //mesaj creare utilizator
        System.out.printf("Utilizatorul %s %s cu id-ul %s a fost creat", this.nume, this.prenume, this.id);

    }

    public void addCont(Cont cont) {
        this.conturi.add(cont);
    }

    public String getId() {
        return this.id;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public boolean validarePin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash); //bool daca e egal sau nu
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Eroare pin");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public void printAccountSummary() {
        System.out.printf("\n\nIstoricul tranzactiilor lui %s %s \n", this.getNume(), this.getPrenume());
        for(int i = 0; i < this.conturi.size(); i++){
            System.out.printf("");
            System.out.printf("%d) %s\n", i + 1, this.conturi.get(i).getTranzactie());
        }
        System.out.println();
    }

    public int numarConturi() {
        return this.conturi.size();
    }

    public void istoricCont(int index) {
        this.conturi.get(index).afiseazaIstoricTranz();
    }

    public double getBalantaCont(int index) {
        return this.conturi.get(index).getBalanta();
    }

    public Object getIdCont(int toAcc) {
        return this.conturi.get(toAcc);
    }

    public void addTranzCont(int fromAcc, double suma, String descriere) {
        this.conturi.get(fromAcc).addTranzactie(suma, descriere);
    }
}
