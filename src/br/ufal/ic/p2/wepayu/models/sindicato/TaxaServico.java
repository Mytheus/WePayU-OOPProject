package br.ufal.ic.p2.wepayu.models.sindicato;

import br.ufal.ic.p2.wepayu.Exception.DataInvalidaException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TaxaServico {

    protected LocalDate data;
    protected double valor;

    public TaxaServico(String data, double valor) throws DataInvalidaException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        try {this.data = LocalDate.parse(data, formatter);}
        catch (DateTimeParseException e)
        {
            throw new DataInvalidaException();
        }
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public double getValor() {
        return valor;
    }



}
