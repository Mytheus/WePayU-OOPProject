package br.ufal.ic.p2.wepayu.models.pagamento;

import br.ufal.ic.p2.wepayu.Exception.DescAgendaInvalidaException;

public class AgendaDePagamento {


    protected String regime;
    protected String modo;
    protected int diaSemanaMes;
    protected int intervaloSemanas;

    public AgendaDePagamento(String regime) throws DescAgendaInvalidaException {

        this.regime = regime;
        String[] options = regime.split(" ");
        this.modo = options[0];
        switch (options.length)
        {
            case 1:
                break;
            case 2:
                if (options[1].equals("$")) this.diaSemanaMes = 31;
                else this.diaSemanaMes = Integer.parseInt(options[1]);
                break;
            case 3:
                this.intervaloSemanas = Integer.parseInt(options[1]);
                this.diaSemanaMes = Integer.parseInt(options[2]);
                break;
        }
        if (this.modo.equals("mensal"))
        {
            if ((diaSemanaMes > 28 || diaSemanaMes <1)&& !(options[1].equals("$")))
                throw new DescAgendaInvalidaException();
        }
        else if (this.modo.equals("semanal"))
        {
            if ((diaSemanaMes > 7 || diaSemanaMes < 1) || ((options.length==3) &&
                    (intervaloSemanas > 52 || intervaloSemanas < 1)))
                throw new DescAgendaInvalidaException();
        }
        else throw new DescAgendaInvalidaException();
    }

    public AgendaDePagamento() {
    }

    public String getRegime() {
        return regime;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public int getDiaSemanaMes() {
        return diaSemanaMes;
    }

    public void setDiaSemanaMes(int diaSemanaMes) {
        this.diaSemanaMes = diaSemanaMes;
    }

    public int getIntervaloSemanas() {
        return intervaloSemanas;
    }

    public void setIntervaloSemanas(int intervaloSemanas) {
        this.intervaloSemanas = intervaloSemanas;
    }


}
