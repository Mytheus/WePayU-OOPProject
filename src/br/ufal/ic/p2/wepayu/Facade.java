package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.utils.BusinessLogic;

import java.io.IOException;

public class Facade {

    private BusinessLogic bl;

    public Facade() {
        this.bl = new BusinessLogic();
    }


    public void zerarSistema()
    {
        this.bl.zerarSistema();
    }
    public void encerrarSistema()
    {
    }
    public String criarEmpregado (String nome, String endereco, String tipo, String salario) throws
            EmpregadoNaoExisteException, NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException,
            SalarioDeveSerNaoNegativoException, TipoNaoAplicavelException, ComissaoNaoPodeSerNulaException, SalarioDeveSerNumericoException,
            ComissaoDeveSerNumericaException, TipoInvalidoException, ComissaoDeveSerNaoNegativaException, IOException {
        return this.bl.criarEmpregado(nome, endereco, tipo, salario);
    }
    public String criarEmpregado (String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException, NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException, SalarioDeveSerNaoNegativoException, TipoNaoAplicavelException, ComissaoNaoPodeSerNulaException, SalarioDeveSerNumericoException, ComissaoDeveSerNumericaException, TipoInvalidoException, ComissaoDeveSerNaoNegativaException, IOException {
        return this.bl.criarEmpregado(nome, endereco, tipo, salario, comissao);
    }

    public String getAtributoEmpregado(String emp, String atributo) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, AtributoNaoExisteException, EmpregadoNaoHoristaException, EmpregadoNaoComissionadoException, EmpregadoNaoRecebeBancoException, EmpregadoNaoSindicalizadoException, IOException {
        return this.bl.getAtributoEmpregado(emp, atributo);
    }

    public void alteraEmpregado(String emp, String atributo, String valor1) throws EmpregadoNaoExisteException, EmpregadoNaoHoristaException, EmpregadoNaoComissionadoException, ValorTrueOuFalseException, MetodoPagamentoInvalidoException, ComissaoNaoPodeSerNulaException, ComissaoDeveSerNumericaException, ComissaoDeveSerNaoNegativaException, SalarioDeveSerNaoNegativoException, SalarioNaoPodeSerNuloException, SalarioDeveSerNumericoException, TipoNaoAplicavelException, TipoInvalidoException, EnderecoNaoPodeSerNuloException, NomeNaoPodeSerNuloException, AtributoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, IOException {
        this.bl.alteraEmpregado(emp, atributo, valor1);
    }

    public void alteraEmpregado(String emp, String atributo, boolean valor1, String idSindicato, String taxaSindical) throws EmpregadoNaoExisteException, HaOutroEmpregadoIdMembroException, TaxaSindicalNaoNulaException, TaxaSindicalNaoNegativaException, TaxaSindicalNumericaException, IdSindicatoNaoNuloException, IOException {
        this.bl.alteraEmpregado(emp, atributo, valor1, idSindicato, taxaSindical);
    }

    public void alteraEmpregado(String emp, String atributo, String valor, String comissao) throws EmpregadoNaoExisteException, NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException, IOException {
        this.bl.alteraEmpregado(emp, atributo, valor, comissao);
    }


    public void alteraEmpregado(String emp, String atributo, String valor1, String banco, String agencia,
                                String contaCorrente) throws EmpregadoNaoExisteException, AgenciaNaoNuloException, ContaCorrenteNaoNuloException, BancoNaoNuloException, IOException {
        this.bl.alteraEmpregado(emp, atributo, valor1, banco, agencia, contaCorrente);
    }



    public String getEmpregadoPorNome(String nome, int indice) throws NaoHaEmpregadoComEsseNomeException, IOException {
        return this.bl.getEmpregadoPorNome(nome, indice);
    }

    public void removerEmpregado (String emp) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, IOException {
        this.bl.removerEmpregado(emp);
    }

    public void lancaCartao(String emp, String data, String horas) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, EmpregadoNaoHoristaException, HorasDevemSerPositivasException, DataInvalidaException, EmpregadoNaoComissionadoException, IOException {
        this.bl.lancaCartao(emp, data, horas);
    }

    public void lancaVenda(String emp, String data, String valor) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, DataInvalidaException, EmpregadoNaoHoristaException, EmpregadoNaoComissionadoException, ValorDeveSerPositivoException, IOException {
        this.bl.lancaVenda(emp, data, valor);
    }

    public void lancaTaxaServico(String membro, String data, String valor) throws EmpregadoNaoExisteException,
            IdEmpregadoNaoPodeSerNuloException, DataInvalidaException, MembroNaoExisteException, IdMembroNaoPodeSerNulaException, ValorDeveSerPositivoException, IOException {
        this.bl.lancaTaxaServico(membro, data, valor);
    }

    public String getVendasRealizadas(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException, EmpregadoNaoHoristaException, EmpregadoNaoComissionadoException, IOException {
        return this.bl.getVendasRealizadas(emp, dataInicial, dataFinal);
    }
    public String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, EmpregadoNaoHoristaException, DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException, EmpregadoNaoComissionadoException, IOException {
        return this.bl.getHorasNormaisTrabalhadas(emp, dataInicial, dataFinal);
    }
    public String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, EmpregadoNaoHoristaException, DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException, EmpregadoNaoComissionadoException, IOException {
        return this.bl.getHorasExtrasTrabalhadas(emp, dataInicial, dataFinal);
    }

    public String getTaxasServico(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException, EmpregadoNaoSindicalizadoException, IOException {
        return this.bl.getTaxasServico(emp, dataInicial, dataFinal);
    }

    public String totalFolha(String data) throws DataInvalidaException, DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException, IOException {
        return this.bl.totalFolha(data);
    }

    public void rodaFolha(String data, String saida) throws DataInicialInvalidaException, DataFinalInvalidaException, DataInvalidaException, DataInicialPosteriorFinalException, IOException {
        this.bl.rodaFolha(data, saida);
    }


}
