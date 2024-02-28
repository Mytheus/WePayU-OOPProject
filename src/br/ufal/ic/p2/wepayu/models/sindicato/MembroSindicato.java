package br.ufal.ic.p2.wepayu.models.sindicato;

import br.ufal.ic.p2.wepayu.Exception.DataFinalInvalidaException;
import br.ufal.ic.p2.wepayu.Exception.DataInicialInvalidaException;
import br.ufal.ic.p2.wepayu.Exception.DataInicialPosteriorFinalException;
import br.ufal.ic.p2.wepayu.Exception.DataInvalidaException;
import br.ufal.ic.p2.wepayu.utils.TratamentoEntrada;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MembroSindicato {



    protected String idMembro;
    protected double taxaSindical;

    protected List<TaxaServico> taxas;


    public MembroSindicato(String idMembro, String taxaSindical) {
        this.idMembro = idMembro;
        this.taxaSindical = Double.parseDouble(taxaSindical.replace(",", "."));
        taxas = new ArrayList<>();
    }

    public MembroSindicato() {
    }

    public String getIdMembro() {
        return idMembro;
    }

    public double getTaxaSindical() {
        return taxaSindical;
    }

    public List<TaxaServico> getTaxas() {
        return taxas;
    }

    public void addNewTaxa (String data, String valor) throws DataInvalidaException {
        taxas.add(new TaxaServico(data, valor));
    }

    public double getTaxasServico(String dataInicial, String dataFinal) throws DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException {

        TratamentoEntrada entrada = new TratamentoEntrada();

        LocalDate dataInicialF = entrada.checkData(dataInicial, true);
        LocalDate dataFinalF = entrada.checkData(dataFinal, false);
        if (dataInicialF.isAfter(dataFinalF)) throw new DataInicialPosteriorFinalException();

        double total = 0;
        for (TaxaServico taxa : taxas) {
            LocalDate dataTaxa = taxa.getData();
            if (dataTaxa.isAfter(dataInicialF.minusDays(1)) && dataTaxa.isBefore(dataFinalF))
                total += taxa.getValor();
        }
        return total;

    }

    public void setIdMembro(String idMembro) {
        this.idMembro = idMembro;
    }

    public void setTaxaSindical(double taxaSindical) {
        this.taxaSindical = taxaSindical;
    }

    public void setTaxas(List<TaxaServico> taxas) {
        this.taxas = taxas;
    }
}
