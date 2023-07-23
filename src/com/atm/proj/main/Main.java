package com.atm.proj.main;

import com.atm.proj.classes.Banca;
import com.atm.proj.classes.Cont;
import com.atm.proj.classes.Utilizator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Banca banca = new Banca("BCR");

        Utilizator utilizator = banca.addUtilizator("Alex", "Ionut", "1234");

        Cont cont = new Cont("Economii", utilizator, banca);
        utilizator.addCont(cont);
        banca.addCont(cont);

        //login
        Utilizator utilizatorCurent;
        while(true){
            //login prompt pana e ok
            utilizatorCurent = Main.mainMenuPrompt(banca, scanner);

            //ramane in meniu principal pana iese userul
            Main.printUserMenu(utilizatorCurent, scanner);
        }

    }



    private static Utilizator mainMenuPrompt(Banca banca, Scanner scanner) {
        String id;
        String pin;
        Utilizator utilizator;

        //afiseaza user si pana pana il baga corect
        do{
            System.out.println("\n\nBine ati venit la banca " + banca.getNume());
            System.out.println("Introduceti id-ul: ");
            id = scanner.nextLine();
            System.out.println("Introduceti pin-ul: ");
            pin = scanner.nextLine();

            //gasim obiectul care corespunde cu datele astea
            utilizator = banca.loginUtilizator(id, pin);
            if(utilizator == null){
                System.out.println("Id sau pin incorecte");
                System.out.println("Incercati din nou");
            }

        }while (utilizator == null); //continua loop pana gasim combinatia

        return utilizator;
    }

    private static void printUserMenu(Utilizator utilizatorCurent, Scanner scanner) {
        //afiseaza conturile utilizatouluui
        utilizatorCurent.printAccountSummary();

        //alegere optiuni
        int optiune;

        //meniu utilizator
        do {
            System.out.print("Bine ati revenit, " + utilizatorCurent.getNume() + " " + utilizatorCurent.getPrenume());
            System.out.println(", ce ati dori sa faceti?");
            System.out.println(" 1) Afiseaa istoricul tranzactiilor");
            System.out.println(" 2) Retrage bani");
            System.out.println(" 3) Depune bani");
            System.out.println(" 4) Tansfera bani");
            System.out.println(" 5) IESIRE");
            System.out.println("Introduceti optiunea: ");
            optiune = scanner.nextInt();

            if (optiune < 1 || optiune > 5) {
                System.out.println("Optiune invalida. Introduceti un numar 1-5");
            }
        } while (optiune < 1 || optiune > 5);

        //daca optiunea e buna
        switch (optiune) {

            case 1:
                Main.afiseazaTranzactii(utilizatorCurent, scanner);
                break;
            case 2:
                Main.retragereSuma(utilizatorCurent, scanner);
                break;
            case 3:
                Main.depunereSuma(utilizatorCurent, scanner);
            case 4:
                Main.transferBancar(utilizatorCurent, scanner);
        }

        //reafiseaza meniul daca utilizatorul nu iese
        if (optiune != 5) {
            Main.printUserMenu(utilizatorCurent, scanner);
        }
    }

    private static void depunereSuma(Utilizator utilizatorCurent, Scanner scanner) {
        int toAcc;
        double suma;
        double balanta;
        String descriere;

        //contul care transfera suma
        do{
            System.out.printf("Introduceti numarul (1 - %d) contului in care doriti sa depuneti: ", utilizatorCurent.numarConturi());
            toAcc = scanner.nextInt() - 1;
            if(toAcc < 0 || toAcc >= utilizatorCurent.numarConturi()){
                System.out.println("Incercati din nou");
            }
        }while (toAcc < 0 || toAcc >= utilizatorCurent.numarConturi());

        balanta = utilizatorCurent.getBalantaCont(toAcc);
        //suma pe care o transfera
        do{
            System.out.printf("Introduceti suma pe care doriti sa o depuneti (maxim %.02f llei) : ", balanta);
            suma = scanner.nextDouble();
            if(suma < 0){
                System.out.println("Suma trebuie sa fie mai mare ca 0");
            }
        }while (suma < 0);

        scanner.nextLine();

        //descrierea
        System.out.println("Introduceti o descirere: ");
        descriere = scanner.nextLine();

        //retragrea
        utilizatorCurent.addTranzCont(toAcc, suma, descriere);
    }

    private static void retragereSuma(Utilizator utilizatorCurent, Scanner scanner) {

        int fromAcc;
        double suma;
        double balanta;
        String descriere;

        //contul care transfera suma
        do{
            System.out.printf("Introduceti numarul (1 - %d) contului din care doriti sa retrageti: ", utilizatorCurent.numarConturi());
            fromAcc = scanner.nextInt() - 1;
            if(fromAcc < 0 || fromAcc >= utilizatorCurent.numarConturi()){
                System.out.println("Inceercati din nou");
            }
        }while (fromAcc < 0 || fromAcc >= utilizatorCurent.numarConturi());

        balanta = utilizatorCurent.getBalantaCont(fromAcc);
        //suma pe care o transfera
        do{
            System.out.printf("Introduceti suma pe care doriti sa o retrageti (maxim %.02f llei) : ", balanta);
            suma = scanner.nextDouble();
            if(suma < 0){
                System.out.println("Suma trebuie sa fie mai mare ca 0");
            }else if(suma > balanta){
                System.out.println("Nu aveti suficienti bani in cont");
            }
        }while (suma < 0 || suma > balanta);

        scanner.nextLine();

        //descrierea
        System.out.println("Introduceti o descirere: ");
        descriere = scanner.nextLine();

        //retragrea
        utilizatorCurent.addTranzCont(fromAcc, -1 * suma, descriere);

    }

    private static void transferBancar(Utilizator utilizatorCurent, Scanner scanner) {
        int toAcc;
        int fromAcc;
        double suma;
        double balanta;

        //contul care transfera suma
        do{
            System.out.printf("Introduceti numarul (1 - %d) contului din care doriti sa faceti tranzactia: ", utilizatorCurent.numarConturi());
            fromAcc = scanner.nextInt() - 1;
            if(fromAcc < 0 || fromAcc >= utilizatorCurent.numarConturi()){
                System.out.println("Inceercati din nou");
            }
        }while (fromAcc < 0 || fromAcc >= utilizatorCurent.numarConturi());

        balanta = utilizatorCurent.getBalantaCont(fromAcc);

        //contul unde transferam
        do{
            System.out.printf("Introduceti numarul (1 - %d) contului unde doriti sa faceti tranzactia: ", utilizatorCurent.numarConturi());
            toAcc = scanner.nextInt() - 1;
            if(toAcc < 0 || toAcc >= utilizatorCurent.numarConturi()){
                System.out.println("Inceercati din nou");
            }
        }while (toAcc < 0 || toAcc >= utilizatorCurent.numarConturi());

        //suma pe care vrea sa o transfere
        do{
            System.out.printf("Introduceti suma pe care doriti sa o transferati (maxim %.02f llei) : ", balanta);
            suma = scanner.nextDouble();
            if(suma < 0){
                System.out.println("Suma trebuie sa fie mai mare ca 0");
            }else if(suma > balanta){
                System.out.println("Nu aveti suficienti bani in cont");
            }
        }while (suma < 0 || suma > balanta);

        //transferul in sine
            utilizatorCurent.addTranzCont(fromAcc, -1*suma, String.format("Transfer catre contul: %s ", utilizatorCurent.getIdCont(toAcc)));
            utilizatorCurent.addTranzCont(fromAcc, suma, String.format("Transfer catre contul: %s ", utilizatorCurent.getIdCont(toAcc)));
    }
    
    private static void afiseazaTranzactii(Utilizator utilizatorCurent, Scanner scanner) {
        int nrCont;

        //alege contul la care vrea sa vada tranzactiile
        do{
            System.out.printf("Introduceti numarul contului (1 - %d) dintre conturile dvs. : ", utilizatorCurent.numarConturi());
            nrCont = scanner.nextInt() - 1;
            if(nrCont < 0 || nrCont >= utilizatorCurent.numarConturi()){
                System.out.println("Cont invalid");
            }
        }while (nrCont < 0 || nrCont >= utilizatorCurent.numarConturi());

        //daca e ok afisam tranzactiile
        utilizatorCurent.istoricCont(nrCont);
    }
}
