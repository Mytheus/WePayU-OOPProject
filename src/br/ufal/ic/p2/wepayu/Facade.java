package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.models.empregado.Empregado;
import br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado.EmpregadoHorista;

import java.util.ArrayList;
import java.util.List;

public class Facade {

    protected List<Empregado> empregados;
    static protected int idEmpregado;

    public Facade()
    {
        idEmpregado = 0;
        empregados = new ArrayList<>();
    }

    public String criarEmpregado (String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException {
        Empregado e;
        if (tipo.equals("horista"))
        {
            e = new EmpregadoHorista(nome, endereco, tipo, Double.parseDouble(salario));
        } else
        {
            e = new EmpregadoAssalariado(nome, endereco, tipo, Double.parseDouble(salario));
        }
        empregados.add(idEmpregado, e);
        return Integer.toString(idEmpregado++);
    }
    public String criarEmpregado (String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException {
        Empregado e;
        e = new EmpregadoComissionado(nome, endereco, tipo, Double.parseDouble(salario), Double.parseDouble(comissao));
        empregados.add(idEmpregado, e);
        return Integer.toString(idEmpregado++);
    }

    public String getAtributoEmpregado(String emp, String atributo)
    {
        Empregado e = empregados.get(Integer.parseInt((emp)));
        return switch (atributo) {
            case "nome" -> e.getNome();
            case "endereco" -> e.getEndereco();
            case "tipo" -> e.getTipo();
            case "salario" -> Double.toString(e.getSalario());
            case "sindicalizado" -> Boolean.toString(e.isSindicalizado());
            case "metodoPagamento" -> e.getMetodoPagamento();
            default -> "";
        };
    }

    public void alteraEmpregado(String emp, String atributo, String valor1)
    {
        Empregado e = empregados.get(Integer.parseInt(emp));
        switch (atributo)
        {
            case "nome":
                e.setNome(valor1);
                break;
            case "endereco":
                e.setEndereco(valor1);
                break;
            case "tipo":
                e.setTipo(valor1);
                break;
            case "salario":
                e.setSalario(Double.parseDouble(valor1));
                break;
            case "metodoPagamento":
                e.setMetodoPagamento(valor1);
                break;
        }
        empregados.set(Integer.parseInt(emp), e);
    }

    public void alteraEmpregado(String emp, String atributo, boolean valor1, String idSindicato, String taxaSindical)
    {
        int id = Integer.parseInt(emp);
        Empregado e = empregados.get(id);
        if (valor1)
        {
            e.setSindicalizado(true, idSindicato, taxaSindical);
        }
        empregados.set(id, e);
    }
    public void alteraEmpregado(String emp, String atributo, boolean valor1)
    {
        int id = Integer.parseInt(emp);
        Empregado e = empregados.get(id);
        e.setSindicalizado(valor1);
        empregados.set(id, e);
    }

    public void alteraEmpregado(String emp, String atributo, String valor1, String banco, String agencia,
                                String contaCorrente)
    {
        int id = Integer.parseInt(emp);
        Empregado e = empregados.get(id);
        e.setMetodoPagamento(valor1, banco, agencia, contaCorrente);
        empregados.set(id, e);
    }
    public String getEmpregadoPorNome(String nome, int indice)
    {
        int iterator = 1;
        for (int i = 0; i < empregados.size(); i++)
        {
            if (empregados.get(i).getNome().equalsIgnoreCase(nome) && iterator==indice)
                return Integer.toString(i);
            else if (empregados.get(i).getNome().equalsIgnoreCase(nome))
                i++;
        }
        return "-1";
    }

    public void removerEmpregado (String emp)
    {
        empregados.remove(Integer.parseInt(emp));
    }

    public void lancaCartao(String emp, String data, String horas)
    {
        int id = Integer.parseInt(emp);
        if (empregados.get(id) instanceof EmpregadoHorista e)
        {
            e.addNewPonto(data, horas);
            empregados.set(id, e);
        }
    }

    public void lancaVenda(String emp, String data, String valor)
    {
        int id = Integer.parseInt(emp);
        if (empregados.get(id) instanceof EmpregadoComissionado e)
        {
            e.addNewVenda(data, valor);
            empregados.set(id, e);
        }
    }

    public void lancaServico(String emp, String data, String valor)
    {
        int id = Integer.parseInt(emp);
        if (empregados.get(id).isSindicalizado())
        {
            Empregado e = empregados.get(id);
            e.addNewTaxaServico(data, valor);
            empregados.set(id, e);
        }
    }

    public String getVendasRealizadas(String emp, String dataInicial, String dataFinal)
    {
        double totalVendas = 0;

        if (empregados.get(Integer.parseInt(emp)) instanceof EmpregadoComissionado)
        {
            totalVendas = ((EmpregadoComissionado) empregados.get(Integer.parseInt(emp))).getVendas(dataInicial,
                    dataFinal);
        }

        return Double.toString(totalVendas);
    }
    public String getHorasTrabalhadas(String emp, String dataInicial, String dataFinal)
    {
        double totalHoras = 0;

        if (empregados.get(Integer.parseInt(emp)) instanceof EmpregadoHorista)
        {
            totalHoras = ((EmpregadoHorista) empregados.get(Integer.parseInt(emp))).getHoras(dataInicial,
                    dataFinal, false);
        }

        return Double.toString(totalHoras);
    }
    public String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal)
    {
        double totalHoras = 0;

        if (empregados.get(Integer.parseInt(emp)) instanceof EmpregadoHorista)
        {
            totalHoras = ((EmpregadoHorista) empregados.get(Integer.parseInt(emp))).getHoras(dataInicial,
                    dataFinal, true);
        }

        return Double.toString(totalHoras);
    }

    public String getTaxasServico(String emp, String dataInicial, String dataFinal)
    {
        double totalTaxas = 0;

        if (empregados.get(Integer.parseInt(emp)).isSindicalizado())
            totalTaxas = empregados.get(Integer.parseInt(emp)).getTaxasServico(dataInicial, dataFinal);


        return Double.toString(totalTaxas);
    }


}
