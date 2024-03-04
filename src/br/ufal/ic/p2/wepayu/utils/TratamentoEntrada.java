package br.ufal.ic.p2.wepayu.utils;

import br.ufal.ic.p2.wepayu.Exception.EnderecoNaoPodeSerNuloException;
import br.ufal.ic.p2.wepayu.Exception.NomeNaoPodeSerNuloException;

import br.ufal.ic.p2.wepayu.Exception.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TratamentoEntrada {

    private String id;
    private String nome;
    private String endereco;
    private String tipo;
    private String salario;
    private String taxaDeComissao;

    public TratamentoEntrada (String nome, String endereco, String tipo, String salario)
    {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario.replace(",", ".");
        this.taxaDeComissao = null;
    }
    public TratamentoEntrada (String nome, String endereco, String tipo, String salario,
                              String taxaDeComissao)
    {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario.replace(",", ".");
        this.taxaDeComissao = taxaDeComissao.replace(",", ".");
    }

    public TratamentoEntrada (String id, String tipo)
    {
        this.id = id;
        this.tipo = tipo;
    }
    public TratamentoEntrada(String id)
    {
        this.id = id;
    }

    public TratamentoEntrada() {
    }

    private boolean nomeNulo()
    {
        return this.nome.isEmpty();
    }

    private boolean enderecoNulo()
    {
        return this.endereco.isEmpty();
    }

    private boolean tipoInvalido()
    {
        return !(this.tipo.equals("comissionado") || this.tipo.equals("assalariado") || this.tipo.equals("horista"));
    }

    private boolean tipoNaoAplicavel()
    {
        return ((this.tipo.equals("comissionado") && this.taxaDeComissao==null) || (!this.tipo.equals("comissionado") && this.taxaDeComissao!=null));
    }

    private boolean salarioNulo()
    {
        return this.salario.isEmpty();
    }
    private boolean salarioNaoNumerico()
    {
        return !this.salario.matches(".*\\d.*");
    }

    private boolean salarioNegativo()
    {
        return Double.parseDouble(this.salario) < 0;
    }

    private boolean comissaoNula()
    {
        return this.taxaDeComissao.isEmpty();
    }
    private boolean comissaoNaoNumerico()
    {
        return !this.taxaDeComissao.matches(".*\\d.*");
    }

    private boolean comissaoNegativo()
    {
        return Double.parseDouble(this.taxaDeComissao) < 0;
    }

    private boolean idNulo()
    {
        return this.id.isEmpty();
    }

    private boolean atributoNaoExiste(String atributo)
    {
        String[] atributos = {"nome", "id", "tipo", "endereco", "salario", "comissao", "sindicalizado",
                "metodoPagamento", "idSindicato", "banco", "agencia", "taxaSindical", "contaCorrente",
                "agendaPagamento"};
        for (String s: atributos)
        {
            if (s.equals(atributo)) return false;
        }
        return true;
    }

    private boolean empregadoHorista()
    {
        return this.tipo.equals("horista");
    }
    private boolean empregadoComissionado()
    {
        return this.tipo.equals("comissionado");
    }

    public void fullCheckCriarEmpregado()
            throws TipoInvalidoException, TipoNaoAplicavelException, NomeNaoPodeSerNuloException,
            EnderecoNaoPodeSerNuloException, SalarioDeveSerNaoNegativoException, SalarioDeveSerNumericoException,
            SalarioNaoPodeSerNuloException, ComissaoDeveSerNaoNegativaException, ComissaoDeveSerNumericaException,
            ComissaoNaoPodeSerNulaException
    {
        if (nomeNulo()) throw new NomeNaoPodeSerNuloException();
        else if (enderecoNulo()) throw new EnderecoNaoPodeSerNuloException();
        else if (tipoInvalido()) throw new TipoInvalidoException();
        else if (tipoNaoAplicavel()) throw new TipoNaoAplicavelException();
        else if (salarioNulo()) throw new SalarioNaoPodeSerNuloException();
        else if (salarioNaoNumerico()) throw new SalarioDeveSerNumericoException();
        else if (salarioNegativo()) throw new SalarioDeveSerNaoNegativoException();
        else if (this.taxaDeComissao!= null && comissaoNula()) throw new ComissaoNaoPodeSerNulaException();
        else if (this.taxaDeComissao!= null && comissaoNaoNumerico()) throw new ComissaoDeveSerNumericaException();
        else if (this.taxaDeComissao!= null && comissaoNegativo()) throw new ComissaoDeveSerNaoNegativaException();
    }


    public void checkComissao(String taxaComissao) throws ComissaoNaoPodeSerNulaException, ComissaoDeveSerNumericaException, ComissaoDeveSerNaoNegativaException {
        this.taxaDeComissao = taxaComissao.replace(",", ".");
        if (comissaoNula()) throw new ComissaoNaoPodeSerNulaException();
        else if (comissaoNaoNumerico()) throw new ComissaoDeveSerNumericaException();
        else if (comissaoNegativo()) throw new ComissaoDeveSerNaoNegativaException();
    }

    public void checkSalario(String salario) throws SalarioNaoPodeSerNuloException, SalarioDeveSerNumericoException, SalarioDeveSerNaoNegativoException {
        this.salario = salario;
        if (salarioNulo()) throw new SalarioNaoPodeSerNuloException();
        else if (salarioNaoNumerico()) throw new SalarioDeveSerNumericoException();
        else if (salarioNegativo()) throw new SalarioDeveSerNaoNegativoException();
    }

    public void checkTipo(String tipo) throws TipoInvalidoException, TipoNaoAplicavelException {
        this.tipo = tipo;
        if (tipoInvalido()) throw new TipoInvalidoException();
        else if (tipoNaoAplicavel()) throw new TipoNaoAplicavelException();
    }

    public void checkEndereco(String endereco) throws EnderecoNaoPodeSerNuloException {
        this.endereco = endereco;
        if (enderecoNulo()) throw new EnderecoNaoPodeSerNuloException();
    }

    public void checkNome(String nome) throws NomeNaoPodeSerNuloException {
        this.nome = nome;
        if (nomeNulo()) throw new NomeNaoPodeSerNuloException();
    }

    public void fullCheckGetAtributo(String atributo) throws IdEmpregadoNaoPodeSerNuloException, AtributoNaoExisteException {
        if (idNulo()) throw new IdEmpregadoNaoPodeSerNuloException();
        else if (atributoNaoExiste(atributo)) throw new AtributoNaoExisteException();
    }

    public void checkIdEmpregado() throws IdEmpregadoNaoPodeSerNuloException
    {
        if (idNulo()) throw new IdEmpregadoNaoPodeSerNuloException();
    }

    public void checkIdMembro() throws IdMembroNaoPodeSerNulaException {
        if (idNulo()) throw new IdMembroNaoPodeSerNulaException();
    }

    public void checkTipoEmpregado(String tipo) throws EmpregadoNaoHoristaException, EmpregadoNaoComissionadoException {
        //System.out.println("Entra : " + tipo);
        switch (tipo)
        {
            case "horista":
                if (!empregadoHorista()) throw new EmpregadoNaoHoristaException();
                break;
            case "comissionado":
                if (!empregadoComissionado()) throw new EmpregadoNaoComissionadoException();
                break;
        }
    }

    public void checkHoras(String horas) throws HorasDevemSerPositivasException
    {

        if (Double.parseDouble(horas.replace(",", "."))<=0)
        {
            throw new HorasDevemSerPositivasException();
        }
    }

    public LocalDate checkData(String data, boolean inicial) throws DataInicialInvalidaException, DataFinalInvalidaException {
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-M-d");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d/M/yyyy");
        if(data.contains("-"))
        {
            String[] a = data.split("-");
            if (Integer.parseInt(a[2])>=30 && a[1].equals("2")) {
                if (inicial) throw new DataInicialInvalidaException();
                else throw new DataFinalInvalidaException();
            }
        }
        else
        {
            String[] a = data.split("/");
            if (Integer.parseInt(a[0])>=30 && a[1].equals("2")) {
                if (inicial) throw new DataInicialInvalidaException();
                else throw new DataFinalInvalidaException();
            }
        }


        LocalDate dataF;
        if (data.contains("-")) {
            if (inicial) {
                try {
                    dataF = LocalDate.parse(data, formatter1);
                } catch (DateTimeParseException e) {
                    throw new DataInicialInvalidaException();
                }
            } else {
                try {
                    dataF = LocalDate.parse(data, formatter1);
                } catch (DateTimeParseException e) {
                    throw new DataFinalInvalidaException();
                }
            }
        }
        else
        {
            if (inicial) {
                try {
                    dataF = LocalDate.parse(data, formatter2);
                } catch (DateTimeParseException e) {
                    throw new DataInicialInvalidaException();
                }
            } else {
                try {
                    dataF = LocalDate.parse(data, formatter2);
                } catch (DateTimeParseException e) {
                    throw new DataFinalInvalidaException();
                }
            }
        }
        return dataF;
    }

    public void checkValorVenda(String valor) throws ValorDeveSerPositivoException {
        if (Double.parseDouble(valor.replace(",", ".")) <=0) throw new ValorDeveSerPositivoException();
    }

    public void checkAlteraAtributoSindical(String taxaSindical, String idSindicato) throws TaxaSindicalNaoNegativaException, TaxaSindicalNumericaException, TaxaSindicalNaoNulaException, IdSindicatoNaoNuloException {
        if (taxaSindical.isEmpty()) throw new TaxaSindicalNaoNulaException();
        else if (!taxaSindical.matches(".*\\d.*")) throw new TaxaSindicalNumericaException();
        else if (Double.parseDouble(taxaSindical.replace(",", ".")) <=0 ) throw new TaxaSindicalNaoNegativaException();
        else if (idSindicato.isEmpty()) throw new IdSindicatoNaoNuloException();
    }

    public void checkAlteraAtributosBanco(String banco, String agencia, String contaCorrente) throws BancoNaoNuloException, AgenciaNaoNuloException, ContaCorrenteNaoNuloException {
        if (banco.isEmpty()) throw new BancoNaoNuloException();
        else if (agencia.isEmpty()) throw new AgenciaNaoNuloException();
        else if (contaCorrente.isEmpty()) throw new ContaCorrenteNaoNuloException();
    }

    public void checkMetodoPagamento(String metodoPagamento) throws MetodoPagamentoInvalidoException {
        if (!(metodoPagamento.equals("emMaos") || metodoPagamento.equals("correios") || metodoPagamento.equals("banco"))) {
            throw new MetodoPagamentoInvalidoException();
        }
    }


}
