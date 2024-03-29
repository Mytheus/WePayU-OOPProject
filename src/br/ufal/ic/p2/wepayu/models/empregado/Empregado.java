package br.ufal.ic.p2.wepayu.models.empregado;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.models.pagamento.AgendaDePagamento;
import br.ufal.ic.p2.wepayu.models.pagamento.MetodoPagamento;
import br.ufal.ic.p2.wepayu.models.pagamento.tipoPagamento.Banco;
import br.ufal.ic.p2.wepayu.models.pagamento.tipoPagamento.Correios;
import br.ufal.ic.p2.wepayu.models.pagamento.tipoPagamento.EmMaos;
import br.ufal.ic.p2.wepayu.models.sindicato.MembroSindicato;

public class Empregado implements Comparable<Empregado> {


    protected String id;
    protected static int countId = 0;
    protected String nome;
    protected String endereco;
    protected String tipo;
    protected double salario;




    protected String dataUltimoPagamento;
    protected AgendaDePagamento agendaPagamento;


    protected String sindicalizado;



    protected MembroSindicato membroSindicato;




    protected MetodoPagamento metodoPagamento;


    public Empregado(String nome, String endereco, String tipo, String salario)
            throws EmpregadoNaoExisteException, NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException {
        this.id = Integer.toString(countId++);
        if (nome.isEmpty()) throw new NomeNaoPodeSerNuloException();
        this.nome = nome;
        if (endereco.isEmpty()) throw new EnderecoNaoPodeSerNuloException();
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = Double.parseDouble(salario.replace(",", "."));
        this.metodoPagamento = new EmMaos();
        this.sindicalizado = "false";
        this.dataUltimoPagamento = null;
    }

    public Empregado() {
    }


    public String getDataUltimoPagamento() {
        return dataUltimoPagamento;
    }

    public void setDataUltimoPagamento(String dataUltimoPagamento) {
        this.dataUltimoPagamento = dataUltimoPagamento;
    }

    public AgendaDePagamento getAgendaPagamento() {
        return agendaPagamento;
    }

    public void setAgendaPagamento(AgendaDePagamento agendaPagamento) {
        this.agendaPagamento = agendaPagamento;
    }
    public static int getCountId() {
        return countId;
    }

    public static void setCountId(int countId) {
        Empregado.countId = countId;
    }

    public String getId() {
        return id;
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

    public MembroSindicato getMembroSindicato() {
        return membroSindicato;
    }

    public String getSindicalizado() {
        return sindicalizado;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }
    public String getMetodoPagamentoName() {
        return this.metodoPagamento.getMetodoPagamento();
    }
    public MetodoPagamento getObjMetodoPagamento() { return this.metodoPagamento;}

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
    public void setSalario(String salario) {
        this.salario = Double.parseDouble(salario.replace(",", "."));
    }


    public void setSindicalizado(String sindicalizado) {
        this.sindicalizado = sindicalizado;
    }
    public void mudaSindicalizado(String sindicalizado, String idSindicato, String taxaSindical) {
        this.setSindicalizado(sindicalizado);
        this.setMembroSindicato(new MembroSindicato(idSindicato, taxaSindical));
    }

    public void setMembroSindicato(MembroSindicato membroSindicato) {
        this.membroSindicato = membroSindicato;
    }

    public void setMetodoPagamento(String banco, String agencia, String contaCorrente)
    {
        this.metodoPagamento = new Banco(banco, agencia, contaCorrente);
    }
    public void setMetodoPagamento(String valor1)
    {
        if (valor1.equals("emMaos"))
        {
            metodoPagamento = new EmMaos();
        }
        else
        {
            metodoPagamento = new Correios();
        }
    }


    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }


    public void addNewTaxaServico(String data, String valor) throws DataInvalidaException {
        membroSindicato.addNewTaxa(data, valor);
    }




    public double getTaxasServico(String dataInicial, String dataFinal) throws DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException {
        return membroSindicato.getTaxasServico(dataInicial, dataFinal);
    }


    @Override
    public int compareTo(Empregado o) {
        return this.nome.compareTo(o.nome);
    }
}
