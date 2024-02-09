package br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.TratamentoEntrada;
import br.ufal.ic.p2.wepayu.models.ResultadoDeVenda;
import br.ufal.ic.p2.wepayu.models.empregado.Empregado;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmpregadoComissionado extends Empregado {


    protected double taxaDeComissao;
    protected List<ResultadoDeVenda> vendas;

    public EmpregadoComissionado(String id,String nome, String endereco, String tipo, String salario,
                                 String taxaDeComissao) throws EmpregadoNaoExisteException, NomeNaoPodeSerNuloException,
            EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException {
        super(id, nome, endereco, tipo, salario);
        this.taxaDeComissao = Double.parseDouble(taxaDeComissao);
        vendas = new ArrayList<>();
    }

    public double getTaxaDeComissao() {
        return taxaDeComissao;
    }

    public void setTaxaDeComissao(double taxaDeComissao) {
        this.taxaDeComissao = taxaDeComissao;
    }

    public void addNewVenda(String data, String valor) throws DataInvalidaException {
        vendas.add(new ResultadoDeVenda(data, Double.parseDouble(valor)));
    }

    public double getVendas(String dataInicial, String dataFinal) throws DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException {
        TratamentoEntrada entrada = new TratamentoEntrada();

        LocalDate dataInicialF = entrada.checkData(dataInicial, true);
        LocalDate dataFinalF = entrada.checkData(dataFinal, false);

        if (dataInicialF.isAfter(dataFinalF)) throw new DataInicialPosteriorFinalException();
        double total = 0;
        for (ResultadoDeVenda venda : vendas) {
            LocalDate dataVenda = venda.getData();
            if (dataVenda.isAfter(dataInicialF.minusDays(1)) && dataVenda.isBefore(dataFinalF))
                total += venda.getValor();
        }
        return total;
    }
}
