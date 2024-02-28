package br.ufal.ic.p2.wepayu.models;
import br.ufal.ic.p2.wepayu.Exception.DataInvalidaException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CartaoDePonto {


    protected LocalDate data;
    protected Double horas;
    public CartaoDePonto(String data, String horas) throws DataInvalidaException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        try {
            this.data = LocalDate.parse(data, formatter);
        }catch (DateTimeParseException e)
        {
            throw new DataInvalidaException();
        }
        this.horas = Double.parseDouble(horas.replace(",", "."));
    }

    public CartaoDePonto() {
    }

    public LocalDate getData() {
        return data;
    }

    public Double getHoras() {
        return horas;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }
}
