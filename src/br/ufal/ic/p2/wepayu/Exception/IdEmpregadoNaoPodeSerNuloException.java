package br.ufal.ic.p2.wepayu.Exception;

public class IdEmpregadoNaoPodeSerNuloException extends Exception{
    public IdEmpregadoNaoPodeSerNuloException() {
        super("Identificacao do empregado nao pode ser nula.");
    }
}
