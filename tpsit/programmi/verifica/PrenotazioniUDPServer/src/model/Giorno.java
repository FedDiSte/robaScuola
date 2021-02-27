package model;

public class Giorno {

    int giorno;
    String mese;
    boolean[] turni = new boolean[12];

    public Giorno (int giorno) {
        this.giorno = giorno;
        mese = "Febbraio";
        for(int i = 0; i < 12; i++) {
            turni[i] = false;
        }
    }

    public int getGiorno() {
        return giorno;
    }

    public String getMese() {
        return mese;
    }

    public boolean isPrenotato(int turno) {
        return turni[turno];
    }

}
