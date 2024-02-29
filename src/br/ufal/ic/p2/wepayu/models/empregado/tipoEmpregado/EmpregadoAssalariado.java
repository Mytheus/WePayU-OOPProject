package br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.models.empregado.Empregado;
import br.ufal.ic.p2.wepayu.utils.InfoFolha;

import java.time.LocalDate;

public class EmpregadoAssalariado extends Empregado {
    public EmpregadoAssalariado(String id,String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException, NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException {
        super(id, nome, endereco, tipo, salario);
    }

    public EmpregadoAssalariado() {
    }

    public InfoFolha getInfo(LocalDate data) throws DataInicialPosteriorFinalException, DataInicialInvalidaException,
            DataFinalInvalidaException {

        LocalDate dataInicial = data.minusDays(31);
        double salBruto = this.salario;

        double desconto = 0;
        if(Boolean.parseBoolean(this.getSindicalizado()))
        {
            double taxasAdc = this.getMembroSindicato().getTaxasServico(dataInicial.toString(),
                    data.toString());
            desconto = this.getMembroSindicato().getTaxaSindical() * data.getMonth().length(data.isLeapYear());
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
        return new InfoFolha(this.nome, salBruto, desconto, salLiq, metodo);
    }
}
