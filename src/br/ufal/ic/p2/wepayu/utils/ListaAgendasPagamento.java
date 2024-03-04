package br.ufal.ic.p2.wepayu.utils;

import br.ufal.ic.p2.wepayu.models.pagamento.AgendaDePagamento;

import java.util.ArrayList;
import java.util.List;

public class ListaAgendasPagamento {


    List<AgendaDePagamento> agendas;

    public ListaAgendasPagamento()
    {
        this.agendas = new ArrayList<>();
    }

    public List<AgendaDePagamento> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<AgendaDePagamento> agendas) {
        this.agendas = agendas;
    }

    public boolean hasAgenda(String regime)
    {
        for (AgendaDePagamento agenda: this.agendas)
        {
            if (agenda.getRegime().equals(regime)) return true;
        }
        return false;
    }

    public void addAgenda(AgendaDePagamento agenda)
    {
        this.agendas.add(agenda);
    }

}
