/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.escaperoomdefinitivo;

import File.FileExeption;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author work
 */
public class Main {

    public static void main(String[] args) throws FileExeption {

        String[] vociMenu = new String[7];
        int sceltaUtente = -1;

        Scanner tastiera = new Scanner(System.in);

        EscapeRoom e1 = new EscapeRoom();

        int esitoOperazione;

        String nomeFileCSV = "EscapeRoom.txt";
        String nomeFileBinario = "EscapeRoom.bin";

        vociMenu[0] = "Esci";
        vociMenu[1] = "Aggiungi persona in stanza";
        vociMenu[2] = "Eliminare persona in base al codice identificativo";
        vociMenu[3] = "Visualizzare le uscite dalla room";
        vociMenu[4] = "Visualizzare permanenza di una persona scelta";
        vociMenu[5] = "Visualizzare in ordine alfabetico le persone presenti";
        vociMenu[6] = "Esportare in CSV";
        Menu menu = new Menu(vociMenu);

        //deserializzazione
        try {

            e1 = e1.caricaAccessi(nomeFileBinario);
            System.out.println("Dati caricati correttamente");
        } catch (IOException ex) {
            System.out.println("Impossibile accedere al file. I dati non sono stati caricati");
        } catch (FileExeption ex) {
            System.out.println("Errore di lettura dal file. I dati non sono stati caricati");
        }

        do {
            try {
                sceltaUtente = menu.sceltaMenu();
                switch (sceltaUtente) {
                    case 0: {
                        System.out.println("L'applicazione terminerà");
                        break;
                    }
                    case 1: {

                        String nome, cognome;
                        int codiceFiscale;
                        int anno;
                        int mese;
                        int giorno;
                        int ora;
                        int minuto;
                        try {
                            System.out.println("Inserire il nome qui--> ");
                            nome = tastiera.next();
                            System.out.println("Inserire il cognome qui--> ");
                            cognome = tastiera.next();
                            System.out.println("Inserire l'anno di ingresso qui--> ");
                            anno = tastiera.nextInt();
                            System.out.println("Inserire il mese di ingresso qui--> ");
                            mese = tastiera.nextInt();
                            System.out.println("Inserire il giorno di ingresso qui--> ");
                            giorno = tastiera.nextInt();
                            System.out.println("Inserire l'ora di ingresso qui--> ");
                            ora = tastiera.nextInt();
                            System.out.println("Inserire il minuto di ingresso qui--> ");
                            minuto = tastiera.nextInt();
                            LocalDateTime dataAccesso = LocalDateTime.of(anno, mese, giorno, ora, minuto);

                            System.out.println("Inserire il codiceFisale qui--> ");
                            codiceFiscale = tastiera.nextInt();
                            Accesso accesso = new Accesso(nome, cognome, codiceFiscale, dataAccesso);
                            int i = e1.aggiungiAccesso(accesso);
                            if (i == 0) {
                                System.out.println("Accesso aggiunto con successo");
                            } else if (i == 100) {
                                System.out.println("Escape Room piena");
                            } else {
                                System.out.println("Accesso NON aggiunto");
                            }
                        } catch (java.time.DateTimeException erroreData) {
                            throw new FileExeption("Errore ");
                        }

                        break;
                    }

                    case 2: {

                        int anno;
                        int mese;
                        int giorno;
                        int ora;
                        int minuto;
                        int codiceFiscale;
                        try {
                            System.out.println("Inserire il codiceFisale qui--> ");
                            codiceFiscale = tastiera.nextInt();

                            System.out.println("Inserire l'anno di uscita qui--> ");
                            anno = tastiera.nextInt();
                            System.out.println("Inserire il mese di uscita qui--> ");
                            mese = tastiera.nextInt();
                            System.out.println("Inserire il giorno di uscita qui--> ");
                            giorno = tastiera.nextInt();
                            System.out.println("Inserire l'ora di uscita qui--> ");
                            ora = tastiera.nextInt();
                            System.out.println("Inserire il minuto di uscita qui--> ");
                            minuto = tastiera.nextInt();
                            LocalDateTime dataUscita = LocalDateTime.of(anno, mese, giorno, ora, minuto);
                            e1.eliminaAccesso(codiceFiscale, dataUscita);

                        } catch (java.time.DateTimeException erroreData) {
                            throw new FileExeption("Errore ");
                        }

                        break;
                    }
                    case 3: {

                        System.out.println("Le uscite sono: ");

                        e1.visualizzaUscite();

                        break;
                    }
                    case 4: {
                        String nome;
                        String cognome;

                        System.out.println("Inserire il nome della persona di cui si vuole visualizzare la permanenza qui--> ");
                        nome = tastiera.nextLine();
                        System.out.println("Inserire il cognome qui--> ");
                        cognome = tastiera.nextLine();

                        e1.visualizzaPermanenza(cognome, nome);
                        break;
                    }
                    case 5: {
                        Accesso[] accessi;
                        accessi = e1.visualizzaOrdineAlfabetico();
                        for (int i = 0; i < accessi.length; i++) {

                            if (accessi[i] != null) {
                                System.out.println(accessi[i].toString());
                            }

                        }
                        break;
                    }
                    case 6: {
                        try {
                            e1.esportaAccessoCSV();
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }

                }

            } catch (InputMismatchException | NumberFormatException ex) {
                tastiera.nextLine();
                System.out.println("Input non corretto");
            }

        } while (sceltaUtente != 0);

    }

}
