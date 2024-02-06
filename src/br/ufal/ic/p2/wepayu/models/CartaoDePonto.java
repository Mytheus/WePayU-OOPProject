package br.ufal.ic.p2.wepayu.models;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CartaoDePonto {
    protected LocalDate data;
    protected String horas;
    public CartaoDePonto(String data, String horas) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.data = LocalDate.parse(data, formatter);
        this.horas = horas;
    }

    public LocalDate getData() {
        return data;
    }

    public String getHoras() {
        return horas;
    }
}
