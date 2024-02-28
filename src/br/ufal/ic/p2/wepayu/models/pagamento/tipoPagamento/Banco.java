package br.ufal.ic.p2.wepayu.models.pagamento.tipoPagamento;

import br.ufal.ic.p2.wepayu.models.pagamento.MetodoPagamento;

public class Banco extends MetodoPagamento {

    protected String banco;
    protected String agencia;
    protected String contaCorrente;

    public Banco(String banco, String agencia, String contaCorrente) {
        this.banco = banco;
        this.agencia = agencia;
        this.contaCorrente = contaCorrente;
    }

    public Banco() {
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(String contaCorrente) {
        this.contaCorrente = contaCorrente;
    }




    @Override
    public String getMetodoPagamento() {
        return "banco";
    }

}
