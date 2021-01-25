package model;

import java.util.ArrayList;

public class Utente {

    private String email;
    private String password;

    private ArrayList<CartaDiCredito> carteDiCredito;

    private ArrayList<Spesa> speseEffettuate;

    private double valoreCashback;

    public Utente(String email, String password, ArrayList<CartaDiCredito> carteDiCredito) {
        this.email = email;
        this.password = password;
        this.carteDiCredito = carteDiCredito;
        speseEffettuate = new ArrayList<>();
    }

    public Utente(String email, String password, ArrayList<CartaDiCredito> carteDiCredito, ArrayList<Spesa> speseEffettuate) {
        this.email = email;
        this.password = password;
        this.carteDiCredito = carteDiCredito;
        this.speseEffettuate = speseEffettuate;
    }

    public String getEmail()  {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<CartaDiCredito> getCarteDiCredito() {
        return carteDiCredito;
    }

    public void addCartaDiCredito(CartaDiCredito carta) {
        carteDiCredito.add(carta);
    }

    public ArrayList<Spesa> getSpeseEffettuate() {
        return speseEffettuate;
    }

    public void addSpesaEffettuate(Spesa spesa) {
        speseEffettuate.add(spesa);
    }

    public double getValoreCashback() {
        valoreCashback = 0;
        for (Spesa spesa : speseEffettuate) {
            valoreCashback += (spesa.getPrezzo() * 10) / 100;
        }
        return valoreCashback;
    }

}
