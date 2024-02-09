package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.models.empregado.Empregado;
import br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.Persistence;
import br.ufal.ic.p2.wepayu.models.pagamento.tipoPagamento.Banco;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.util.ArrayList;
import java.util.List;

public class Facade {

    //protected List<Empregado> empregados;
    static protected int idEmpregado;
    protected Persistence persistence;

    protected XStream xstream;

    public Facade() {
        this.persistence = new Persistence("C:\\Users\\mathe\\Downloads\\WePayU\\XMLFiles\\empregados.xml");
        xstream = new XStream();
        xstream.alias("empregado", Empregado.class);
        xstream.alias("empregadoHorista", EmpregadoHorista.class);
        xstream.alias("empregadoComissionado", EmpregadoComissionado.class);
        xstream.alias("empregadoAssalariado", EmpregadoAssalariado.class);
        xstream.alias("empregados", ListaEmpregados.class);
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.addImplicitCollection(ListaEmpregados.class, "list");
    }


    public void zerarSistema()
    {
        this.persistence = new Persistence("C:\\Users\\mathe\\Downloads\\WePayU\\XMLFiles\\empregados.xml");
        idEmpregado = 0;
        persistence.createXMLFile();
        xstream = new XStream();
        xstream.alias("empregado", Empregado.class);
        xstream.alias("empregadoHorista", EmpregadoHorista.class);
        xstream.alias("empregadoComissionado", EmpregadoComissionado.class);
        xstream.alias("empregadoAssalariado", EmpregadoAssalariado.class);
        xstream.alias("empregados", ListaEmpregados.class);
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.addImplicitCollection(ListaEmpregados.class, "list");
    }
    public void encerrarSistema()
    {
    }
    public String criarEmpregado (String nome, String endereco, String tipo, String salario) throws
            EmpregadoNaoExisteException, NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException,
            SalarioDeveSerNaoNegativoException, TipoNaoAplicavelException, ComissaoNaoPodeSerNulaException, SalarioDeveSerNumericoException,
            ComissaoDeveSerNumericaException, TipoInvalidoException, ComissaoDeveSerNaoNegativaException
    {
        Empregado e;
        salario = salario.replace(",", ".");
        String id = Integer.toString(idEmpregado);
        TratamentoEntrada entrada = new TratamentoEntrada(id, nome, endereco, tipo, salario);
        entrada.fullCheckCriarEmpregado();
        //System.out.println(salario);
        if (tipo.equals("horista"))
        {
            e = new EmpregadoHorista(id ,nome, endereco, tipo, salario);
        } else
        {
            e = new EmpregadoAssalariado(id, nome, endereco, tipo, salario);
        }
        String xml = persistence.readXMLFile();
        ListaEmpregados list;
        if (!xml.isEmpty()) list = (ListaEmpregados)xstream.fromXML(xml);
        else list = new ListaEmpregados();
        list.add(e);
        xml = xstream.toXML(list);
        persistence.writeToXMLFile(xml);
        //empregados.add(idEmpregado, e);
        return Integer.toString(idEmpregado++);
    }
    public String criarEmpregado (String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException, NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException, SalarioDeveSerNaoNegativoException, TipoNaoAplicavelException, ComissaoNaoPodeSerNulaException, SalarioDeveSerNumericoException, ComissaoDeveSerNumericaException, TipoInvalidoException, ComissaoDeveSerNaoNegativaException {
        Empregado e;
        String id = Integer.toString(idEmpregado);
        salario = salario.replace(",", ".");
        comissao = comissao.replace(",", ".");
        TratamentoEntrada entrada = new TratamentoEntrada(id, nome, endereco, tipo, salario, comissao);
        entrada.fullCheckCriarEmpregado();
        //System.out.println(salario);
        //System.out.println(comissao);
        e = new EmpregadoComissionado(id, nome, endereco, tipo, salario,
                comissao);
        String xml = persistence.readXMLFile();
        ListaEmpregados list;
        if (!xml.isEmpty()) list = (ListaEmpregados)xstream.fromXML(xml);
        else list = new ListaEmpregados();
        list.add(e);
        xml = xstream.toXML(list);
        persistence.writeToXMLFile(xml);
        return Integer.toString(idEmpregado++);
    }

    public String getAtributoEmpregado(String emp, String atributo) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, AtributoNaoExisteException, EmpregadoNaoHoristaException, EmpregadoNaoComissionadoException, EmpregadoNaoRecebeBancoException, EmpregadoNaoSindicalizadoException {
        String xml = persistence.readXMLFile();
        //System.out.println(xml);
        ListaEmpregados list;
        if(!xml.isEmpty())  list = (ListaEmpregados) xstream.fromXML(xml);
        else list = new ListaEmpregados();
        //System.out.println(list.size());
        Empregado e = null;
        TratamentoEntrada entrada = new TratamentoEntrada(emp);
        entrada.fullCheckGetAtributo(atributo);
        e = list.searchEmpregado(emp);
        entrada = new TratamentoEntrada(emp, e.getTipo());

        switch (atributo) {
            case "nome" :
                return e.getNome();
            case "endereco" :
                return e.getEndereco();
            case "tipo" :
                return e.getTipo();
            case "salario" :
                return String.format("%,.2f", e.getSalario()).replace(".", "");
            case "sindicalizado" :
                return Boolean.toString(e.isSindicalizado());
            case "metodoPagamento" :
                return e.getMetodoPagamento();
            case "comissao" :
                entrada.checkTipoEmpregado("comissionado");
                return String.format("%,.2f",((EmpregadoComissionado) e).getTaxaDeComissao()).replace(".", "");
            case "banco":
                if (!e.getMetodoPagamento().equals("banco")) throw new EmpregadoNaoRecebeBancoException();
                return ((Banco) e.getObjMetodoPagamento()).getBanco();
            case "idSindicato":
                if (!e.isSindicalizado()) throw new EmpregadoNaoSindicalizadoException();
                return e.getSindicato().getIdMembro();
            case "agencia":
                if (!e.getMetodoPagamento().equals("banco")) throw new EmpregadoNaoRecebeBancoException();
                return ((Banco) e.getObjMetodoPagamento()).getAgencia();
            case "contaCorrente":
                if (!e.getMetodoPagamento().equals("banco")) throw new EmpregadoNaoRecebeBancoException();
                return ((Banco) e.getObjMetodoPagamento()).getContaCorrente();
            case "taxaSindical":
                if (!e.isSindicalizado()) throw new EmpregadoNaoSindicalizadoException();
                return String.format("%,.2f", e.getSindicato().getTaxaSindical()).replace(".", "");
            default:
                return "";
        }
    }

    public void alteraEmpregado(String emp, String atributo, String valor1) throws EmpregadoNaoExisteException, EmpregadoNaoHoristaException, EmpregadoNaoComissionadoException, ValorTrueOuFalseException, MetodoPagamentoInvalidoException, ComissaoNaoPodeSerNulaException, ComissaoDeveSerNumericaException, ComissaoDeveSerNaoNegativaException, SalarioDeveSerNaoNegativoException, SalarioNaoPodeSerNuloException, SalarioDeveSerNumericoException, TipoNaoAplicavelException, TipoInvalidoException, EnderecoNaoPodeSerNuloException, NomeNaoPodeSerNuloException, AtributoNaoExisteException, IdEmpregadoNaoPodeSerNuloException {
        ListaEmpregados empregados = (ListaEmpregados) xstream.fromXML(persistence.readXMLFile());

        TratamentoEntrada entrada = new TratamentoEntrada(emp);
        entrada.checkIdEmpregado();

        Empregado e = empregados.searchEmpregado(emp);

        entrada = new TratamentoEntrada(emp, e.getTipo());
        //System.out.println("Atributo: " +  atributo + ", Valor: " + valor1 + ", tipoAtual: " + e.getTipo());
        switch (atributo)
        {
            case "nome":
                entrada.checkNome(valor1);
                e.setNome(valor1);
                break;
            case "endereco":
                entrada.checkEndereco(valor1);
                e.setEndereco(valor1);
                break;
            case "tipo":
                entrada.checkTipo(valor1);
                e.setTipo(valor1);
                break;
            case "salario":
                entrada.checkSalario(valor1);
                e.setSalario(Double.parseDouble(valor1));
                break;
            case "metodoPagamento":
                entrada.checkMetodoPagamento(valor1);
                e.setMetodoPagamento(valor1);
                break;
            case "comissao":
                entrada.checkTipoEmpregado("comissionado");
                entrada.checkComissao(valor1.replace(",", "."));
                ((EmpregadoComissionado) e).setTaxaDeComissao(Double.parseDouble(valor1.replace(",", ".")));
                break;
            case "sindicalizado":
                if (valor1.equals("true") || valor1.equals("false")) e.setSindicalizado(Boolean.parseBoolean(valor1));
                else throw new ValorTrueOuFalseException();
                break;
            default:
                throw new AtributoNaoExisteException();
        }
        empregados.set(emp, e);
        persistence.writeToXMLFile(xstream.toXML(empregados));
    }

    public void alteraEmpregado(String emp, String atributo, boolean valor1, String idSindicato, String taxaSindical) throws EmpregadoNaoExisteException, HaOutroEmpregadoIdMembroException, TaxaSindicalNaoNulaException, TaxaSindicalNaoNegativaException, TaxaSindicalNumericaException, IdSindicatoNaoNuloException {
        TratamentoEntrada entrada = new TratamentoEntrada();
        entrada.checkAlteraAtributoSindical(taxaSindical, idSindicato);
        taxaSindical = taxaSindical.replace(",", ".");
        ListaEmpregados empregados = (ListaEmpregados) xstream.fromXML(persistence.readXMLFile());
        Empregado e = empregados.searchEmpregado(emp);
        empregados.checkMembroId(idSindicato);
        if (valor1)
        {
            e.setSindicalizado(true, idSindicato, taxaSindical);
        }
        empregados.set(emp, e);
        persistence.writeToXMLFile(xstream.toXML(empregados));
    }

    public void alteraEmpregado(String emp, String atributo, String valor, String comissao) throws EmpregadoNaoExisteException, NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException {
        ListaEmpregados empregados = (ListaEmpregados) xstream.fromXML(persistence.readXMLFile());
        Empregado e = empregados.searchEmpregado(emp);
        if (valor.equals("comissionado")) {
            if (e instanceof EmpregadoHorista || e instanceof EmpregadoAssalariado) {
                //System.out.println("Entra aqui");
                e = new EmpregadoComissionado(e.getId(), e.getNome(), e.getEndereco(), valor,
                        Double.toString(e.getSalario()), comissao.replace(",", "."));
            } else {
                e.setTipo("comissionado");
                ((EmpregadoComissionado) e).setTaxaDeComissao(Double.parseDouble(comissao.replace(",", ".")));
            }
        }
        else if (valor.equals("horista"))
        {
            if (e instanceof EmpregadoComissionado || e instanceof EmpregadoAssalariado) {
                //System.out.println("Entra aqui");
                e = new EmpregadoHorista(e.getId(), e.getNome(), e.getEndereco(), valor, comissao.replace(",", "."));
            } else {
                e.setTipo("horista");
                ((EmpregadoHorista) e).setSalario(Double.parseDouble(comissao.replace(",", ".")));
            }
        }
        empregados.set(emp, e);
        //System.out.println("aaa" + empregados.searchEmpregado(emp).getTipo());
        persistence.writeToXMLFile(xstream.toXML(empregados));
    }


    public void alteraEmpregado(String emp, String atributo, String valor1, String banco, String agencia,
                                String contaCorrente) throws EmpregadoNaoExisteException, AgenciaNaoNuloException, ContaCorrenteNaoNuloException, BancoNaoNuloException {
        ListaEmpregados empregados = (ListaEmpregados) xstream.fromXML(persistence.readXMLFile());
        Empregado e = empregados.searchEmpregado(emp);
        TratamentoEntrada entrada = new TratamentoEntrada();
        entrada.checkAlteraAtributosBanco(banco, agencia, contaCorrente);

        e.setMetodoPagamento(valor1, banco, agencia, contaCorrente);

        empregados.set(emp, e);
        persistence.writeToXMLFile(xstream.toXML(empregados));
    }



    public String getEmpregadoPorNome(String nome, int indice) throws NaoHaEmpregadoComEsseNomeException {
        ListaEmpregados empregados = (ListaEmpregados) xstream.fromXML(persistence.readXMLFile());
        int iterator = 1;
        Empregado e;
        return empregados.searchEmpregadoByName(nome, indice);
    }

    public void removerEmpregado (String emp) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException {
        TratamentoEntrada entrada = new TratamentoEntrada(emp);
        entrada.checkIdEmpregado();
        ListaEmpregados empregados = (ListaEmpregados) xstream.fromXML(persistence.readXMLFile());
        empregados.remove(emp);
        persistence.writeToXMLFile(xstream.toXML(empregados));
    }

    public void lancaCartao(String emp, String data, String horas) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, EmpregadoNaoHoristaException, HorasDevemSerPositivasException, DataInvalidaException, EmpregadoNaoComissionadoException {
        horas = horas.replace(",", ".");
        TratamentoEntrada entrada = new TratamentoEntrada(emp);
        entrada.checkIdEmpregado();
        ListaEmpregados empregados = (ListaEmpregados) xstream.fromXML(persistence.readXMLFile());
        Empregado e = empregados.searchEmpregado(emp);
        entrada = new TratamentoEntrada(e.getId(), e.getTipo());
        entrada.checkTipoEmpregado("horista");
        entrada.checkHoras(horas);
        if (e instanceof EmpregadoHorista)
        {
            //System.out.println(data + " " + horas);
            ((EmpregadoHorista) e).addNewPonto(data, horas);
            empregados.set(emp, e);
        }
        persistence.writeToXMLFile(xstream.toXML(empregados));
    }

    public void lancaVenda(String emp, String data, String valor) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, DataInvalidaException, EmpregadoNaoHoristaException, EmpregadoNaoComissionadoException, ValorDeveSerPositivoException {
        valor = valor.replace(",", ".");
        TratamentoEntrada entrada = new TratamentoEntrada(emp);
        entrada.checkIdEmpregado();
        entrada.checkValorVenda(valor);
        ListaEmpregados empregados = (ListaEmpregados) xstream.fromXML(persistence.readXMLFile());
        Empregado e = empregados.searchEmpregado(emp);
        entrada = new TratamentoEntrada(e.getId(), e.getTipo());
        entrada.checkTipoEmpregado("comissionado");
        if (e instanceof EmpregadoComissionado)
        {
            ((EmpregadoComissionado) e).addNewVenda(data, valor);
            empregados.set(emp, e);
        }
        persistence.writeToXMLFile(xstream.toXML(empregados));
    }

    public void lancaTaxaServico(String membro, String data, String valor) throws EmpregadoNaoExisteException,
            IdEmpregadoNaoPodeSerNuloException, DataInvalidaException, MembroNaoExisteException, IdMembroNaoPodeSerNulaException, ValorDeveSerPositivoException {
        valor = valor.replace(",", ".");
        TratamentoEntrada entrada = new TratamentoEntrada(membro);
        entrada.checkIdMembro();
        entrada.checkValorVenda(valor);
        ListaEmpregados empregados = (ListaEmpregados) xstream.fromXML(persistence.readXMLFile());
        Empregado e = empregados.searchEmpregadoMembro(membro);
        if (e.isSindicalizado())
        {
            e.addNewTaxaServico(data, valor);
            empregados.set(e.getId(), e);
        }
        persistence.writeToXMLFile(xstream.toXML(empregados));
    }

    public String getVendasRealizadas(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException, EmpregadoNaoHoristaException, EmpregadoNaoComissionadoException {
        TratamentoEntrada entrada = new TratamentoEntrada(emp);
        entrada.checkIdEmpregado();
        double totalVendas = 0;
        ListaEmpregados empregados = (ListaEmpregados) xstream.fromXML(persistence.readXMLFile());
        Empregado e = empregados.searchEmpregado(emp);
        entrada = new TratamentoEntrada(e.getId(), e.getTipo());
        entrada.checkTipoEmpregado("comissionado");
        if (e instanceof EmpregadoComissionado)
        {
            totalVendas = ((EmpregadoComissionado) empregados.searchEmpregado(emp)).getVendas(dataInicial,
                    dataFinal);
        }

        return String.format("%,.2f", totalVendas).replace(".", "");
    }
    public String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, EmpregadoNaoHoristaException, DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException, EmpregadoNaoComissionadoException {
        TratamentoEntrada entrada = new TratamentoEntrada(emp);
        entrada.checkIdEmpregado();
        ListaEmpregados empregados = (ListaEmpregados) xstream.fromXML(persistence.readXMLFile());
        Empregado e = empregados.searchEmpregado(emp);
        //System.out.println(e.getNome());
        entrada = new TratamentoEntrada(e.getId(), e.getTipo());
        entrada.checkTipoEmpregado("horista");
        double totalHoras = 0;
        if (e instanceof EmpregadoHorista)
        {
            totalHoras = ((EmpregadoHorista) e).getHoras(dataInicial, dataFinal, false);
        }
        return (totalHoras % 1 !=0 && totalHoras!=0? String.format("%,.1f", totalHoras):
            Integer.toString((int)totalHoras));
    }
    public String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, EmpregadoNaoHoristaException, DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException, EmpregadoNaoComissionadoException {
        TratamentoEntrada entrada = new TratamentoEntrada(emp);
        entrada.checkIdEmpregado();
        double totalHoras = 0;
        ListaEmpregados empregados = (ListaEmpregados) xstream.fromXML(persistence.readXMLFile());
        Empregado e = empregados.searchEmpregado(emp);
        entrada = new TratamentoEntrada(e.getId(), e.getTipo());
        entrada.checkTipoEmpregado("horista");
        if (e instanceof EmpregadoHorista)
            totalHoras = ((EmpregadoHorista) e).getHoras(dataInicial, dataFinal, true);
        return (totalHoras % 1 !=0 && totalHoras!=0? String.format("%,.1f", totalHoras):
                Integer.toString((int)totalHoras));
    }

    public String getTaxasServico(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException, EmpregadoNaoSindicalizadoException {
        TratamentoEntrada entrada = new TratamentoEntrada(emp);
        entrada.checkIdEmpregado();
        double totalTaxas = 0;
        ListaEmpregados empregados = (ListaEmpregados) xstream.fromXML(persistence.readXMLFile());
        Empregado e = empregados.searchEmpregado(emp);
        if (e.isSindicalizado())
            totalTaxas = e.getTaxasServico(dataInicial, dataFinal);
        else {
            throw new EmpregadoNaoSindicalizadoException();
        }


        return String.format("%,.2f", totalTaxas).replace(".", "");
    }


}
