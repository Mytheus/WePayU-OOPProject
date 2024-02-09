package br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.EnderecoNaoPodeSerNuloException;
import br.ufal.ic.p2.wepayu.Exception.NomeNaoPodeSerNuloException;
import br.ufal.ic.p2.wepayu.Exception.SalarioNaoPodeSerNuloException;
import br.ufal.ic.p2.wepayu.models.empregado.Empregado;

public class EmpregadoAssalariado extends Empregado {
    public EmpregadoAssalariado(String id,String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException, NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException {
        super(id, nome, endereco, tipo, salario);
    }
}
