package br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.utils.InfoFolha;
import br.ufal.ic.p2.wepayu.utils.TratamentoEntrada;
import br.ufal.ic.p2.wepayu.models.CartaoDePonto;
import br.ufal.ic.p2.wepayu.models.empregado.Empregado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmpregadoHorista extends Empregado {



    protected List<CartaoDePonto> pontos;
    public EmpregadoHorista(String id, String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException, NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException {
        super(id, nome, endereco, tipo, salario);
        pontos = new ArrayList<>();
    }

    public EmpregadoHorista() {
    }

    public List<CartaoDePonto> getPontos() {
        return pontos;
    }

    public void setPontos(List<CartaoDePonto> pontos) {
        this.pontos = pontos;
    }

    public void addNewPonto(String data, String horas) throws DataInvalidaException {
        pontos.add(new CartaoDePonto(data, horas));
    }

    private double totalHoras(boolean extras, LocalDate dataInicialF, LocalDate dataFinalF) {
        double total = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        for (CartaoDePonto ponto : pontos) {

            LocalDate dataHoras = LocalDate.parse(ponto.getData(), formatter);
            double horas = ponto.getHoras();


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

    public double getHoras(String dataInicial, String dataFinal, boolean extras) throws DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException {

        TratamentoEntrada entrada = new TratamentoEntrada();

        LocalDate dataInicialF = entrada.checkData(dataInicial, true);
        LocalDate dataFinalF = entrada.checkData(dataFinal, false);

        if (dataInicialF.isAfter(dataFinalF)) throw new DataInicialPosteriorFinalException();

        return totalHoras(extras, dataInicialF, dataFinalF);
    }



    public double getHoras(LocalDate dataInicialF, LocalDate dataFinalF, boolean extras) throws
            DataInicialPosteriorFinalException {


        if (dataInicialF.isAfter(dataFinalF)) throw new DataInicialPosteriorFinalException();

        return totalHoras(extras, dataInicialF, dataFinalF);
    }

    public InfoFolha getInfo(LocalDate data) throws DataInicialPosteriorFinalException, DataInicialInvalidaException,
            DataFinalInvalidaException {
        LocalDate dataInicial = data.minusDays(7);

        int horas = (int)this.getHoras(dataInicial, data, false);
        int extra = (int)(this.getHoras(dataInicial, data, true));

        double salBruto = horas*this.getSalario() + extra*this.getSalario()*1.5;

        double desconto = 0;
        if(Boolean.parseBoolean(this.getSindicalizado()))
        {
            double taxasAdc = this.getMembroSindicato().getTaxasServico(dataInicial.toString(),
                            data.toString());
            desconto = this.getMembroSindicato().getTaxaSindical() * 7;
            desconto = desconto + taxasAdc;
        }
        double salLiq = 0;
        if(salBruto-desconto>=0)
        {
            salLiq = salBruto-desconto;
        }
        else {
            desconto = 0;
        }

        String metodo = this.getMetodoPagamentoName();
        return new InfoFolha(this.nome, horas, extra, salBruto, desconto, salLiq, metodo);
    }

}
