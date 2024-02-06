package br.ufal.ic.p2.wepayu.models;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class ResultadoDeVenda {
    protected LocalDate data;
    protected double valor;

    public ResultadoDeVenda(String data, double valor) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.data = LocalDate.parse(data, formatter);
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public double getValor() {
        return valor;
    }

}
