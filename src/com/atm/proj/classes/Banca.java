package com.atm.proj.classes;

import java.util.ArrayList;
import java.util.Random;

public class Banca {
    private String nume;
    private ArrayList<Utilizator> clienti;
    private ArrayList<Cont> conturiClienti;

    public Banca(String nume) {
        this.nume = nume;
        this.clienti = new ArrayList<>();
        this.conturiClienti = new ArrayList<>();
    }

    public String getIdNouUtilizator(){
        //trebuie sa verificam ca nu exista deja
        String id;
        Random random = new Random();
        int len = 10;
        boolean gasit;

        do{
            //generqm un numar
            id = "";
            for(int i = 0; i < len; i++){
                id += ((Integer)random.nextInt(10)).toString();
            }

            //verific daca mai exista
            gasit = false;
            for(Utilizator utilizator : this.clienti){
                if(id.compareTo(utilizator.getId()) == 0){
                    gasit = true;
                    break;
                }
            }
        }while(gasit);

        return id;
    }

    public String getIdNouCont(){
//trebuie sa verificam ca nu exista deja
        String id;
        Random random = new Random();
        int len = 10;
        boolean gasit;

        do{
            //generqm un numar
            id = "";
            for(int i = 0; i < len; i++){
                id += ((Integer)random.nextInt(10)).toString();
            }

            //verific daca mai exista
            gasit = false;
            for(Cont cont : this.conturiClienti){
                if(id.compareTo(cont.getId()) == 0){
                    gasit = true;
                    break;
                }
            }
        }while(gasit);

        return id;
    }

    public void addCont(Cont cont){
        this.conturiClienti.add(cont);
    }

    //metoda pt creare utilizator la banca
    public Utilizator addUtilizator(String nume, String prenume, String pin){
        //cream utilizator nou
        Utilizator utilizator = new Utilizator(nume, prenume, pin, this);
        this.clienti.add(utilizator);

        //ii cream si un cont de economii
        Cont cont = new Cont("Economii", utilizator, this);
        utilizator.addCont(cont);
        this.conturiClienti.add(cont);

        return utilizator;
    }

    //metoda de login
    public Utilizator loginUtilizator(String id, String pin){
        //returneaza utilizator daca e corect, null daca nu

        //cautam id-ul in lista
        for(Utilizator utilizator : clienti){
            if(utilizator.getId().compareTo(id) == 0 && utilizator.validarePin(pin)){
                return utilizator;
            }
        }
        return null;
    }

    public String getNume() {
        return nume;
    }
}
