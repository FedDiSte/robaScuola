package model;

public class CartaDiCredito {

    String numero;
    String cvv;
    String dataDiScadenza;

    public CartaDiCredito(String numero, String cvv, String dataDiScadenza) {
        this.numero = numero;
        this.cvv = cvv;
        this.dataDiScadenza = dataDiScadenza;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getDataDiScadenza() {
        return dataDiScadenza;
    }

    public void setDataDiScadenza(String dataDiScadenza) {
        this.dataDiScadenza = dataDiScadenza;
    }
}
