package br.ufal.ic.p2.wepayu.Exception;

public class NaoHaComandoUndoException extends Exception{
    public NaoHaComandoUndoException() {
        super("Nao ha comando a desfazer.");
    }
}
