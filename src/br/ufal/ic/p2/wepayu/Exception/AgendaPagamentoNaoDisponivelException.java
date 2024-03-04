package br.ufal.ic.p2.wepayu.Exception;

public class AgendaPagamentoNaoDisponivelException extends Exception{
    public AgendaPagamentoNaoDisponivelException() {
        super("Agenda de pagamento nao esta disponivel");
    }
}
