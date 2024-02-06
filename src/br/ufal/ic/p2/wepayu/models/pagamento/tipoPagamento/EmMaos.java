package br.ufal.ic.p2.wepayu.models.pagamento.tipoPagamento;

import br.ufal.ic.p2.wepayu.models.pagamento.MetodoPagamento;

public class EmMaos extends MetodoPagamento {
    @Override
    public String getMetodoPagamento() {
        return "em maos";
    }
}
