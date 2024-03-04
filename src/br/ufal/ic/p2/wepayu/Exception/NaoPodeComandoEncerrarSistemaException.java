package br.ufal.ic.p2.wepayu.Exception;

public class NaoPodeComandoEncerrarSistemaException extends Exception{
    public NaoPodeComandoEncerrarSistemaException() {
        super("Nao pode dar comandos depois de encerrarSistema.");
    }
}
