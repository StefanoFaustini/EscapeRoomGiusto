/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.escaperoomdefinitivo;

import File.FileExeption;

import java.time.*;
import java.io.*;
import java.time.format.DateTimeFormatter;

/**
 * @author work
 */
public class EscapeRoom implements Serializable {

    private Accesso[] elencoAccessi; //Array per indicare il numero dei presenti
    private Accesso[] elencoStanze;  //Array per lo storico degli accessi
    private final int N_MAX_ACCESSI = 3;
    private int accessiPresenti;
    private int stanzeOccupate;

    public EscapeRoom() {
        this.elencoAccessi = new Accesso[100];
        this.elencoStanze = new Accesso[N_MAX_ACCESSI];
        this.accessiPresenti = 0;
        this.stanzeOccupate = 0;
    }

    public Accesso[] getElencoAccessi() {
        return elencoAccessi;
    }

    public int getN_MAX_ACCESSI() {
        return N_MAX_ACCESSI;
    }

    public void setElencoAccessi(Accesso[] elencoAccessi) {
        this.elencoAccessi = elencoAccessi;
    }

    public Accesso[] getElencoStanze() {
        return elencoStanze;
    }

    public int getAccessiPresenti() {
        return accessiPresenti;
    }

    public int getStanzeOccupate() {
        return stanzeOccupate;
    }

    public void setElencoStanze(Accesso[] elencoStanze) {
        this.elencoStanze = elencoStanze;
    }

    public void setAccessiPresenti(int accessiPresenti) {
        this.accessiPresenti = accessiPresenti;
    }

    public void setStanzeOccupate(int stanzeOccupate) {
        this.stanzeOccupate = stanzeOccupate;
    }

    /**
     * @param a1 accesso da passargli tramite main
     * @return ritorna 0 in caso di aggiunta di accesso, altrimenti QUALSIASI
     * errore, ritornerà -1. Se ritorna 100 indica che le stanze son occupate
     * <p>
     * La logica che sta dietro è più semplice del dovuto, se ci sono posti
     * ancora disponibili, aggiunge un accesso nella prima stanza libera,
     * altrimenti dice che tutte le stanze son occupate. Occhio che facendo
     * così, nel momento in cui tu decidi di andare ad eliminare un accesso devi
     * spostare tutti quelli dopo di lui a sinistra, in modo che l'aggiunta
     * funzioni.
     *
     * DA TESTARE
     */
    public int aggiungiAccesso(Accesso a1) {
        if (this.stanzeOccupate < N_MAX_ACCESSI) {
            try {
                elencoStanze[this.stanzeOccupate] = a1;
                this.stanzeOccupate++;
                return 0;
            } catch (Exception ex) {
                return -1;
            }
        } else {
            return 100;
        }
    }
    
    /**
     * 
     * @param codiceFiscale = codice identificativo
     * @param dataUscita = uscita da memorizzare nel secondo array per salvare permanentemente i dati
     * @elencoAccessi = array per memorizzare i dati
     * @return 
     */

    public int eliminaAccesso(int codiceFiscale, LocalDateTime dataUscita) {
        for (int i = 0; i < elencoStanze.length; i++) {
            if (elencoStanze[i] != null) {
                if (elencoStanze[i].getCodiceFiscale() == codiceFiscale) {
                    elencoAccessi[accessiPresenti] = new Accesso(elencoStanze[i]);
                    elencoAccessi[accessiPresenti].setDataUscita(dataUscita);
                    elencoStanze[i] = null;
                    stanzeOccupate--;
                    return 0;
                }
            }
        }
        return -1;
    }

    /**
     *
     */
    public void visualizzaUscite() {
        for (int i = 0; i < elencoAccessi.length; i++) {
            if (elencoAccessi[i] != null) {
                System.out.println(elencoAccessi[i].getDataUscita());
            }
        }
    }

    /**
     *
     * @param cognome = cognome utente che si vuole visualizzare la permanenza
     * @param nome = nome utente che si vuole visualizzare la permanenza return
     * res ritorna la stringa formatter = comando per cambiare da LocalDateTime
     * a stringa
     * @return
     */
    public String visualizzaPermanenza(String cognome, String nome) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm");

        for (int i = 0; i < elencoAccessi.length; i++) {
            if (elencoAccessi[i] != null) {
                if (elencoAccessi[i].getNome().equalsIgnoreCase(nome) && elencoAccessi[i].getCognome().equalsIgnoreCase(cognome)) {
                    String res = "Permanenza: " + elencoAccessi[i].getDataAccesso().format(formatter) + " --- " + elencoAccessi[i].getDataUscita().format(formatter);
                    return res;
                }
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public Accesso[] visualizzaOrdineAlfabetico() {
        int n = 0;
        for (int i = 0; i < this.getStanzeOccupate(); i++) {
            if (elencoStanze[i] != null) {
                n++;
            }
        }
        if (n == 0) {
            return null;
        }
        Accesso[] elencoOrdinato = new Accesso[n];

        int p = 0;

        for (int i = 0; i < this.getStanzeOccupate(); i++) {
            if (elencoStanze[i] != null) {
                elencoOrdinato[p] = elencoStanze[i];
                p++;
            }
        }
        elencoOrdinato = Ordinatore.selectionSortAfabeticoAccessi(elencoOrdinato);

        return elencoOrdinato;
    }

    public void esportaAccessoCSV() throws IOException {
        Accesso accesso;

        String accessi;
        //Gestisce in automatico gli errori
        FileWriter f1 = new FileWriter("Salvataggio.csv");
        // FileWriter f2 = new FileWriter("Storico.csv");

        for (int i = 0; i < N_MAX_ACCESSI; i++) {

            accesso = elencoStanze[i];

            if (accesso.getCodiceFiscale() != 0) {
                accessi = accesso.toCSV();
                f1.append(accessi + "\n");
            }

        }
        f1.flush();//Permette di inserire il contenuto del buffer nello Stream a differenza del close che lo chiude
        f1.close();
    }

    /**
     *
     * @param nomeFile
     * @throws IOException
     */
    public void salvaAccesso(String nomeFile) throws IOException {
        FileOutputStream f1 = new FileOutputStream(nomeFile);

        ObjectOutputStream f2 = new ObjectOutputStream(f1);

        f2.writeObject(this);

        f2.flush();

        f2.close();
    }

    public EscapeRoom caricaAccessi(String nomeFile) throws FileNotFoundException, IOException, FileExeption {
        EscapeRoom s;
        FileInputStream f1 = new FileInputStream(nomeFile);

        ObjectInputStream reader = new ObjectInputStream(f1);

        try {
            s = (EscapeRoom) reader.readObject();
            reader.close();
            return s;

        } catch (ClassNotFoundException ex) {
            reader.close();
            throw new FileExeption("Errore lettura");
        }
    }

}
