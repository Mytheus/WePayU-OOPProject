package br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.utils.InfoFolha;
import br.ufal.ic.p2.wepayu.utils.TratamentoEntrada;
import br.ufal.ic.p2.wepayu.models.ResultadoDeVenda;
import br.ufal.ic.p2.wepayu.models.empregado.Empregado;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

public class EmpregadoComissionado extends Empregado {




    protected double taxaDeComissao;




    protected List<ResultadoDeVenda> vendas;

    public EmpregadoComissionado(String nome, String endereco, String tipo, String salario,
                                 String taxaDeComissao) throws EmpregadoNaoExisteException, NomeNaoPodeSerNuloException,
            EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException {
        super(nome, endereco, tipo, salario);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        for (ResultadoDeVenda venda : vendas) {
            LocalDate dataVenda = LocalDate.parse(venda.getData(), formatter);
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

    public InfoFolha getInfo(LocalDate data) throws DataInicialPosteriorFinalException, DataInicialInvalidaException,
            DataFinalInvalidaException {
        LocalDate dataInicial = data.minusDays(13);


        double fixo = Math.floor(this.getSalario() * 24 / 52 * 100) / 100d;
        double vendas = Math.floor(this.getTotalVendas(dataInicial, data) * 100) / 100d;
        double comissao = Math.floor(vendas * this.getTaxaDeComissao() * 100) / 100d;

        double salBruto =
                comissao + fixo;


        double desconto = 0;
        if(Boolean.parseBoolean(this.getSindicalizado()))
        {
            double taxasAdc = this.getMembroSindicato().getTaxasServico(dataInicial.toString(),
                    data.toString());
            desconto = this.getMembroSindicato().getTaxaSindical() * 14;
            desconto = desconto + taxasAdc;
        }
        double salLiq = 0;
        if(salBruto-desconto>=0)
        {
            salLiq = salBruto-desconto;
        }
        else {
            desconto = 0;
        }

        String metodo = this.getMetodoPagamentoName() + ", " + this.endereco;
        return new InfoFolha(this.nome, salBruto, desconto, salLiq, metodo, fixo, vendas, comissao);
    }
}
