package br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.TratamentoEntrada;
import br.ufal.ic.p2.wepayu.models.CartaoDePonto;
import br.ufal.ic.p2.wepayu.models.empregado.Empregado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmpregadoHorista extends Empregado {

    protected List<CartaoDePonto> pontos;
    public EmpregadoHorista(String id, String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException, NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException {
        super(id, nome, endereco, tipo, salario);
        pontos = new ArrayList<>();
    }

    public void addNewPonto(String data, String horas) throws DataInvalidaException {
        pontos.add(new CartaoDePonto(data, horas));
    }

    public double getHoras(String dataInicial, String dataFinal, boolean extras) throws DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException {

        TratamentoEntrada entrada = new TratamentoEntrada();

        LocalDate dataInicialF = entrada.checkData(dataInicial, true);
        LocalDate dataFinalF = entrada.checkData(dataFinal, false);

        if (dataInicialF.isAfter(dataFinalF)) throw new DataInicialPosteriorFinalException();
        double total = 0;

        for (CartaoDePonto ponto : pontos) {
            LocalDate dataHoras = ponto.getData();
            double horas = Double.parseDouble(ponto.getHoras());;

            if (dataHoras.isAfter(dataInicialF.minusDays(1)) && dataHoras.isBefore(dataFinalF)) {
                if (extras) {
                    if (horas > 8) {
                        total += horas - 8;
                    }
                } else {
                    total += ((horas > 8) ? 8 : horas);
                }
            }
        }
        return total;
    }

}
