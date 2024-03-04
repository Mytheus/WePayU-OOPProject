package br.ufal.ic.p2.wepayu.utils;

import br.ufal.ic.p2.wepayu.Exception.DataInicialPosteriorFinalException;
import br.ufal.ic.p2.wepayu.Exception.DataInvalidaException;
import br.ufal.ic.p2.wepayu.models.empregado.Empregado;
import br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.pagamento.AgendaDePagamento;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class ProcessaPagamento {

    protected Empregado e;
    DateTimeFormatter formatter;

    public ProcessaPagamento(Empregado e)
    {
        this.e = e;
        this.formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    }

    protected double horista(LocalDate dataI, LocalDate dataF) throws DataInicialPosteriorFinalException {
        double total = 0;
        EmpregadoHorista eh = (EmpregadoHorista) e;
        total += eh.getHoras(dataI, dataF, false) * eh.getSalario();
        total += eh.getHoras(dataI, dataF, true) * eh.getSalario() * 1.5;
        return total;
    }

    protected double assalariado()
    {
        return e.getSalario();
    }

    protected double comissionado(LocalDate dataI, LocalDate dataF) throws DataInicialPosteriorFinalException {
        EmpregadoComissionado ec = (EmpregadoComissionado) e;
        return Math.floor(ec.getTotalVendas(dataI, dataF) * ec.getTaxaDeComissao() * 100) / 100d;
    }
    protected double getPayment(LocalDate dataInicial, LocalDate dataF) throws DataInicialPosteriorFinalException {
        return switch (e.getTipo()) {
            case "horista" -> horista(dataInicial, dataF);
            case "assalariado" -> assalariado();
            case "comissionado" -> comissionado(dataInicial, dataF);
            default -> 0;
        };
    }

    /*public double valorEmpregado(String data) throws DataInvalidaException, DataInicialPosteriorFinalException {
        LocalDate dataF;
        LocalDate dataInicial;
        try {
            dataF = LocalDate.parse(data, formatter);
            dataInicial = LocalDate.parse("1/1/2005", formatter);
        }catch (DateTimeParseException e)
        {
            throw new DataInvalidaException();
        }
        switch (e.getAgendaPagamento().getRegime())
        {
            case "mensal $":
                if (dataF.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth() == dataF.getDayOfMonth())
                {
                    dataInicial = LocalDate.parse("1/1/2005", formatter);
                    if (e.getTipo().equals("comissionado"))
                        return getPayment(dataInicial, dataF) + e.getSalario();
                }
                else return 0;
                break;
            case "semanal 5":
                if (dataF.getDayOfWeek() == DayOfWeek.FRIDAY) {
                    dataInicial = dataF.minusDays(7);

                    if (e.getTipo().equals("assalariado"))
                        return (Math.floor(getPayment(dataInicial, dataF) * 12/52 * 100)/100d);
                    else if (e.getTipo().equals("comissionado"))
                        return getPayment(dataInicial, dataF)
                                + Math.floor(e.getSalario() * 12 / 52 * 100) / 100d;

                }
                else return 0;
                break;
            case "semanal 2 5":
                if (dataF.getDayOfWeek() == DayOfWeek.FRIDAY) {
                    if ((ChronoUnit.DAYS.between(dataInicial, dataF) + 1) % 14 == 0) {
                        dataInicial = dataF.minusDays(13);

                        if (e.getTipo().equals("assalariado"))
                            return (Math.floor(getPayment(dataInicial, dataF) * 24/52 * 100)/100d);
                        else if (e.getTipo().equals("comissionado"))
                            return getPayment(dataInicial, dataF)
                                    + Math.floor(e.getSalario() * 24 / 52 * 100) / 100d;
                    }
                    else return 0;
                } else return 0;
                break;
        }
        return getPayment(dataInicial, dataF);
    }*/
    public double valorEmpregado(String data) throws DataInvalidaException, DataInicialPosteriorFinalException {
        LocalDate dataF;
        LocalDate dataInicial;
        try {
            dataF = LocalDate.parse(data, formatter);
            dataInicial = LocalDate.parse("1/1/2005", formatter);
        }catch (DateTimeParseException e)
        {
            throw new DataInvalidaException();
        }
        AgendaDePagamento agenda = e.getAgendaPagamento();
        switch (agenda.getModo())
        {
            case "mensal":
                if (dataF.getDayOfMonth() == agenda.getDiaSemanaMes())
                {
                    dataInicial = dataF.minusDays(30);
                    if (e.getTipo().equals("comissionado"))
                        return getPayment(dataInicial, dataF) + e.getSalario();
                }
                else return 0;
                break;
            case "semanal":
                if (agenda.getRegime().split(" ").length == 2) {
                    if (dataF.getDayOfWeek().getValue() == (agenda.getDiaSemanaMes())) {
                        dataInicial = dataF.minusDays(7);
                        if (e.getTipo().equals("assalariado"))
                            return (Math.floor(getPayment(dataInicial, dataF) * 12 / 52 * 100) / 100d);
                        else if (e.getTipo().equals("comissionado"))
                            return getPayment(dataInicial, dataF)
                                    + Math.floor(e.getSalario() * 12 / 52 * 100) / 100d;

                    }
                    else return 0;
                }
                else{
                    if (dataF.getDayOfWeek().getValue() == (agenda.getDiaSemanaMes()))
                    {
                        //System.out.println(ChronoUnit.WEEKS.between(dataInicial, dataF));
                        //System.out.println(ChronoUnit.WEEKS.between(dataInicial, dataF) % (agenda
                        // .getIntervaloSemanas()-1) == 0);
                        //System.out.println(ChronoUnit.DAYS.between(dataInicial, dataF) + 1 + " " + agenda
                        // .getIntervaloSemanas() * 7);
                        //System.out.println((ChronoUnit.DAYS.between(dataInicial, dataF) % 7) -1);
                        if (ChronoUnit.WEEKS.between(dataInicial, dataF) % (agenda.getIntervaloSemanas()-1) == 0)
                        {
                            dataInicial = dataF.minusDays(7L *agenda.getIntervaloSemanas());
                            if (e.getTipo().equals("assalariado"))
                                return (Math.floor(getPayment(dataInicial, dataF) * 12 *agenda.getIntervaloSemanas()/52 * 100)/100d);
                            else if (e.getTipo().equals("comissionado"))
                                return getPayment(dataInicial, dataF)
                                        + Math.floor(e.getSalario() * 12 *agenda.getIntervaloSemanas()/ 52 * 100) / 100d;
                        }
                        else return 0;
                    }
                    else return 0;
                }
                break;
        }
        return getPayment(dataInicial, dataF);
    }

}
