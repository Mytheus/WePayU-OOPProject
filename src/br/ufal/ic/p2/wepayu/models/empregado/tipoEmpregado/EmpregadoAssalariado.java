package br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.models.empregado.Empregado;

public class EmpregadoAssalariado extends Empregado {
    public EmpregadoAssalariado(String nome, String endereco, String tipo, double salario) throws EmpregadoNaoExisteException {
        super(nome, endereco, tipo, salario);
    }
}
