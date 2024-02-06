package br.ufal.ic.p2.wepayu.models.sindicato;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MembroSindicato {

    protected int idMembro;
    protected double taxaSindical;

    protected List<TaxaServico> taxas;


    public MembroSindicato(int idMembro, double taxaSindical) {
        this.idMembro = idMembro;
        this.taxaSindical = taxaSindical;
        taxas = new ArrayList<>();
    }

    public int getIdMembro() {
        return idMembro;
    }

    public double getTaxaSindical() {
        return taxaSindical;
    }

    public List<TaxaServico> getTaxas() {
        return taxas;
    }

    public void addNewTaxa (String data, String valor)
    {
        taxas.add(new TaxaServico(data, Double.parseDouble(valor)));
    }

    public double getTaxasServico(String dataInicial, String dataFinal)
    {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataInicialF = LocalDate.parse(dataInicial, formatter);
        LocalDate dataFinalF = LocalDate.parse(dataFinal, formatter);
        double total = 0;
        for (TaxaServico taxa : taxas) {
            LocalDate dataTaxa = taxa.getData();
            if (dataTaxa.isAfter(dataInicialF) && dataTaxa.isBefore(dataFinalF))
                total += taxa.getValor();
        }
        return total;

    }
}
