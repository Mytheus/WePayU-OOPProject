package br.ufal.ic.p2.wepayu.Exception;

public class AgendaPagamentoJaExisteException extends Exception{
    public AgendaPagamentoJaExisteException() {
        super("Agenda de pagamentos ja existe");
    }
}
