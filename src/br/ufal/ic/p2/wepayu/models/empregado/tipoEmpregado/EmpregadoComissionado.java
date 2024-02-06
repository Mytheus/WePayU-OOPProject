package br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.models.ResultadoDeVenda;
import br.ufal.ic.p2.wepayu.models.empregado.Empregado;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmpregadoComissionado extends Empregado {
    protected double taxaDeComissao;
    protected List<ResultadoDeVenda> vendas;

    public EmpregadoComissionado(String nome, String endereco, String tipo, double salario, double taxaDeComissao) throws EmpregadoNaoExisteException {
        super(nome, endereco, tipo, salario);
        this.taxaDeComissao = taxaDeComissao;
        vendas = new ArrayList<>();
    }

    public double getTaxaDeComissao() {
        return taxaDeComissao;
    }

    public void addNewVenda(String data, String valor)
    {
        vendas.add(new ResultadoDeVenda(data, Double.parseDouble(valor)));
    }

    public double getVendas(String dataInicial, String dataFinal)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataInicialF = LocalDate.parse(dataInicial, formatter);
        LocalDate dataFinalF = LocalDate.parse(dataFinal, formatter);
        double total = 0;
        for (ResultadoDeVenda venda : vendas) {
            LocalDate dataVenda = venda.getData();
            if (dataVenda.isAfter(dataInicialF) && dataVenda.isBefore(dataFinalF))
                total += venda.getValor();
        }
        return total;
    }
}
