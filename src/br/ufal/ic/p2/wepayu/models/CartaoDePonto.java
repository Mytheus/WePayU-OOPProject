package br.ufal.ic.p2.wepayu.models;
import br.ufal.ic.p2.wepayu.Exception.DataInvalidaException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CartaoDePonto {


    protected String data;
    protected Double horas;
    public CartaoDePonto(String data, String horas) throws DataInvalidaException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        try {
            this.data = LocalDate.parse(data, formatter).toString();
        }catch (DateTimeParseException e)
        {
            throw new DataInvalidaException();
        }
        this.horas = Double.parseDouble(horas.replace(",", "."));
    }

    public CartaoDePonto() {
    }

    public String getData() {
        return data;
    }

    public Double getHoras() {
        return horas;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }
}
