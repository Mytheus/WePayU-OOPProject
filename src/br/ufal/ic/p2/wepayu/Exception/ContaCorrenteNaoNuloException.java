package br.ufal.ic.p2.wepayu.Exception;

public class ContaCorrenteNaoNuloException extends Exception{

    public ContaCorrenteNaoNuloException() {
        super("Conta corrente nao pode ser nulo.");
    }
}
