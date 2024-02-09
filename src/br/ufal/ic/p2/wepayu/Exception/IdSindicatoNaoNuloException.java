package br.ufal.ic.p2.wepayu.Exception;

public class IdSindicatoNaoNuloException extends Exception{
    public IdSindicatoNaoNuloException() {
        super("Identificacao do sindicato nao pode ser nula.");
    }
}
