package br.ufal.ic.p2.wepayu.Exception;

public class IdMembroNaoPodeSerNulaException extends Exception{
    public IdMembroNaoPodeSerNulaException() {
        super("Identificacao do membro nao pode ser nula.");
    }
}
