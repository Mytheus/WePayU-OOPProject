package br.ufal.ic.p2.wepayu.Exception;

public class AgenciaNaoNuloException extends Exception{
    public AgenciaNaoNuloException() {
        super("Agencia nao pode ser nulo.");
    }
}
