package br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.utils.TratamentoEntrada;
import br.ufal.ic.p2.wepayu.models.ResultadoDeVenda;
import br.ufal.ic.p2.wepayu.models.empregado.Empregado;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

public class EmpregadoComissionado extends Empregado {




    protected double taxaDeComissao;




    protected List<ResultadoDeVenda> vendas;

    public EmpregadoComissionado(String id,String nome, String endereco, String tipo, String salario,
                                 String taxaDeComissao) throws EmpregadoNaoExisteException, NomeNaoPodeSerNuloException,
            EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException {
        super(id, nome, endereco, tipo, salario);
        this.taxaDeComissao = Double.parseDouble(taxaDeComissao.replace(",", "."));
        vendas = new ArrayList<>();
    }

    public EmpregadoComissionado() {
    }

    public List<ResultadoDeVenda> getVendas() {
        return vendas;
    }

    public double getTaxaDeComissao() {
        return taxaDeComissao;
    }

    public void setTaxaDeComissao(String taxaDeComissao) {
        this.taxaDeComissao = Double.parseDouble(taxaDeComissao.replace(",", "."));
    }

    public void setTaxaDeComissao(double taxaDeComissao) {
        this.taxaDeComissao = taxaDeComissao;
    }

    public void addNewVenda(String data, String valor) throws DataInvalidaException {
        vendas.add(new ResultadoDeVenda(data, valor));
    }


    private double totalVendas(LocalDate dataInicialF, LocalDate dataFinalF) throws DataInicialPosteriorFinalException {
        if (dataInicialF.isAfter(dataFinalF)) throw new DataInicialPosteriorFinalException();
        double total = 0;
        for (ResultadoDeVenda venda : vendas) {
            LocalDate dataVenda = venda.getData();
            if (dataVenda.isAfter(dataInicialF.minusDays(1)) && dataVenda.isBefore(dataFinalF))
                total += venda.getValor();
        }
        return total;
    }
    public double getTotalVendas(String dataInicial, String dataFinal) throws DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException {
        TratamentoEntrada entrada = new TratamentoEntrada();

        LocalDate dataInicialF = entrada.checkData(dataInicial, true);
        LocalDate dataFinalF = entrada.checkData(dataFinal, false);

        return totalVendas(dataInicialF, dataFinalF);
    }

    public double getTotalVendas(LocalDate dataInicialF, LocalDate dataFinalF) throws
            DataInicialPosteriorFinalException {
        return totalVendas(dataInicialF, dataFinalF);
    }

    public void setVendas(List<ResultadoDeVenda> vendas) {
        this.vendas = vendas;
    }
}
