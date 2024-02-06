package br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.models.CartaoDePonto;
import br.ufal.ic.p2.wepayu.models.empregado.Empregado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmpregadoHorista extends Empregado {

    protected List<CartaoDePonto> pontos;
    public EmpregadoHorista(String nome, String endereco, String tipo, double salario) throws EmpregadoNaoExisteException {
        super(nome, endereco, tipo, salario);
        pontos = new ArrayList<>();
    }

    public void addNewPonto(String data, String horas)
    {
        pontos.add(new CartaoDePonto(data, horas));
    }

    public double getHoras(String dataInicial, String dataFinal, boolean extras)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataInicialF = LocalDate.parse(dataInicial, formatter);
        LocalDate dataFinalF = LocalDate.parse(dataFinal, formatter);
        double total = 0;
        for (CartaoDePonto ponto : pontos) {
            LocalDate dataHoras = ponto.getData();
            if (dataHoras.isAfter(dataInicialF) && dataHoras.isBefore(dataFinalF))
                if (extras) {
                    if (Double.parseDouble(ponto.getHoras()) > 8)
                        total += Double.parseDouble(ponto.getHoras()) - 8;
                } else
                    total += (Double.parseDouble(ponto.getHoras()) > 8 ? 8 :
                            Double.parseDouble(ponto.getHoras()));
        }
        return total;
    }

}
