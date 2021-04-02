package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Persona implements Serializable {

    private static int numPersone;
    private String nome;
    private String cognome;
    private int id;
    private LocalDate dataNascita;

    static {
        numPersone = 0;
    }

    public Persona(String nome, String cognome, LocalDate dataNascita) {
        this.nome = nome;
        this.cognome = cognome;
        id = numPersone++;
        this.dataNascita = dataNascita;
    }

    public Persona(String nome, String cognome, LocalDate dataNascita, int id) {
        this.nome = nome;
        this.cognome = cognome;
        this.id = id;
        this.dataNascita = dataNascita;
        if (id > numPersone) {
            numPersone = id + 1;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    @Override
    public String toString() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return "ID: " + id + "\r" +
                "Cognome: " + cognome + "\r" +
                "Nome: " + nome + "\r" +
                "Data di nascita: " + f.format(dataNascita) + "\r";
    }

}
