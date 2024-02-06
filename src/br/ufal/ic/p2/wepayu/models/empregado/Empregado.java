package br.ufal.ic.p2.wepayu.models.empregado;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.models.pagamento.MetodoPagamento;
import br.ufal.ic.p2.wepayu.models.sindicato.MembroSindicato;
import br.ufal.ic.p2.wepayu.models.pagamento.tipoPagamento.Banco;
import br.ufal.ic.p2.wepayu.models.pagamento.tipoPagamento.Correios;
import br.ufal.ic.p2.wepayu.models.pagamento.tipoPagamento.EmMaos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Empregado {

    protected String nome;
    protected String endereco;
    protected String tipo;
    protected double salario;

    protected boolean sindicalizado;
    protected MembroSindicato membroSindicato;


    protected MetodoPagamento metodoPagamento;


    public Empregado(String nome, String endereco, String tipo, double salario) throws EmpregadoNaoExisteException {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        sindicalizado = false;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTipo() {
        return tipo;
    }

    public double getSalario() {
        return salario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public boolean isSindicalizado() {
        return sindicalizado;
    }

    public void setSindicalizado(boolean sindicalizado) {
        this.sindicalizado = sindicalizado;
    }
    public void setSindicalizado(boolean sindicalizado, String idSindicato, String taxaSindical) {
        this.sindicalizado = sindicalizado;
        membroSindicato = new MembroSindicato(Integer.parseInt(idSindicato), Double.parseDouble(taxaSindical));
    }
    public MembroSindicato getSindicato() {
        return membroSindicato;
    }

    public void addNewTaxaServico(String data, String valor)
    {
        membroSindicato.addNewTaxa(data, valor);
    }

    public String getMetodoPagamento() {
        return metodoPagamento.getMetodoPagamento();
    }

    public void setMetodoPagamento(String valor1, String banco, String agencia, String contaCorrente)
    {
        metodoPagamento = new Banco(banco, agencia, contaCorrente);
    }
    public void setMetodoPagamento(String valor1)
    {
        if (valor1.equals("emmaos"))
        {
            metodoPagamento = new EmMaos();
        }
        else
        {
            metodoPagamento = new Correios();
        }
    }

    public double getTaxasServico(String dataInicial, String dataFinal)
    {
        return membroSindicato.getTaxasServico(dataInicial, dataFinal);
    }


}
