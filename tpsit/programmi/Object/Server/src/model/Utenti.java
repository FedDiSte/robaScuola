package model;

import java.util.ArrayList;
import java.util.List;

public class Utenti {

    private List<Persona> utenti;

    public Utenti() {
        utenti = new ArrayList<>();
    }

    public int size() {
        return utenti.size();
    }

    public Persona get(int id) {
        Persona p = utenti.get(id);
        return new Persona(p.getNome(), p.getCognome(), p.getDataNascita(), p.getId());
    }

    public List<Persona> list() {
        List<Persona> result = new ArrayList<>();
        for(Persona p : utenti) {
            result.add(new Persona(p.getNome(), p.getCognome(), p.getDataNascita(), p.getId()));
        }
        return result;
    }

    public void insert(Persona p) {
        utenti.add(p);
    }

    public Persona search(String cognome, String nome) {
        Persona result = null;
        for (Persona p : utenti) {
            if(p.getNome().equals(nome) && p.getCognome().equals(cognome)) {
                result = new Persona(p.getNome(), p.getCognome(), p.getDataNascita(), p.getId());
            }
        }
        return result;
    }

}
