package br.ufal.ic.p2.wepayu.models;
import br.ufal.ic.p2.wepayu.Exception.DataInvalidaException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CartaoDePonto {
    protected LocalDate data;
    protected String horas;
    public CartaoDePonto(String data, String horas) throws DataInvalidaException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        try {
            this.data = LocalDate.parse(data, formatter);
        }catch (DateTimeParseException e)
        {
            throw new DataInvalidaException();
        }
        this.horas = horas;
    }

    public LocalDate getData() {
        return data;
    }

    public String getHoras() {
        return horas;
    }
}
