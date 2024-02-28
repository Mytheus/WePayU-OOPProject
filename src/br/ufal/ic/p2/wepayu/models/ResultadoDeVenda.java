package br.ufal.ic.p2.wepayu.models;
import br.ufal.ic.p2.wepayu.Exception.DataInvalidaException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ResultadoDeVenda {


    protected String data;
    protected double valor;

    public ResultadoDeVenda(String data, String valor) throws DataInvalidaException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        try{
        this.data = LocalDate.parse(data, formatter).toString();}
        catch (DateTimeParseException e)
        {
            throw new DataInvalidaException();
        }
        this.valor = Double.parseDouble(valor.replace(",", "."));
    }

    public ResultadoDeVenda() {
    }

    public String getData() {
        return data;
    }

    public double getValor() {
        return valor;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
