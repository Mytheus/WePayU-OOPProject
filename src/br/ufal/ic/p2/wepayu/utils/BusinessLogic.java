package br.ufal.ic.p2.wepayu.utils;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.models.empregado.Empregado;
import br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.empregado.tipoEmpregado.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.pagamento.tipoPagamento.Banco;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class BusinessLogic {



    static protected int idEmpregado;
        protected Persistence persistence;

        protected XStream xstream;

        public BusinessLogic() {
            this.persistence = new Persistence("XMLFiles\\empregados.xml");
            xstream = new XStream();
            xstream.alias("empregado", Empregado.class);
            xstream.alias("empregadoHorista", EmpregadoHorista.class);
            xstream.alias("empregadoComissionado", EmpregadoComissionado.class);
            xstream.alias("empregadoAssalariado", EmpregadoAssalariado.class);
            xstream.alias("empregados", ListaEmpregados.class);
            xstream.addPermission(AnyTypePermission.ANY);
            xstream.addImplicitCollection(ListaEmpregados.class, "list");
        }

    private String getIdEmpregado() {
        return Integer.toString(idEmpregado);
    }

        public void zerarSistema()
        {
            this.persistence = new Persistence("XMLFiles\\empregados.xml");
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


        private void addEmpregadoXML(Empregado e)
        {
            String xml = persistence.readXMLFile();
            ListaEmpregados list;
            if (!xml.isEmpty()) list = (ListaEmpregados)xstream.fromXML(xml);
            else list = new ListaEmpregados();
            list.add(e);
            xml = xstream.toXML(list);
            persistence.writeToXMLFile(xml);
        }
        private ListaEmpregados readXML()
        {
            String xml = persistence.readXMLFile();

            return (!xml.isEmpty()? (ListaEmpregados) xstream.fromXML(xml): null);
        }

        private void writeToXML(ListaEmpregados empregados)
        {
            persistence.writeToXMLFile(xstream.toXML(empregados));
        }

        private String formatDoubleOutput(double value)
        {
            return String.format("%,.2f", value).replace(".", "");
        }


        //Função para empregado assalariado e horista
        public String criarEmpregado (String nome, String endereco, String tipo, String salario) throws
                EmpregadoNaoExisteException, NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException,
                SalarioDeveSerNaoNegativoException, TipoNaoAplicavelException, ComissaoNaoPodeSerNulaException, SalarioDeveSerNumericoException,
                ComissaoDeveSerNumericaException, TipoInvalidoException, ComissaoDeveSerNaoNegativaException
        {
            //Declara empregado
            Empregado e;
            //Armazena id
            String id = getIdEmpregado();
            //Verifica a entrada
            TratamentoEntrada entrada = new TratamentoEntrada(id, nome, endereco, tipo, salario);
            entrada.fullCheckCriarEmpregado();
            //Cria classe a partir do tipo
            if (tipo.equals("horista"))
            {
                e = new EmpregadoHorista(id ,nome, endereco, tipo, salario);
            }
            else
            {
                e = new EmpregadoAssalariado(id, nome, endereco, tipo, salario);
            }
            //Adiciona ao xml
            addEmpregadoXML(e);
            //Incrementa id e retorna
            String idAtual = getIdEmpregado();
            idEmpregado++;
            return idAtual;
        }

        //Função para empregado comissionado
        public String criarEmpregado (String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException, NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException, SalarioDeveSerNaoNegativoException, TipoNaoAplicavelException, ComissaoNaoPodeSerNulaException, SalarioDeveSerNumericoException, ComissaoDeveSerNumericaException, TipoInvalidoException, ComissaoDeveSerNaoNegativaException {
            //Declara empregado
            Empregado e;
            //Armazena id
            String id = getIdEmpregado();
            //Tratamento de entrada
            TratamentoEntrada entrada = new TratamentoEntrada(id, nome, endereco, tipo, salario, comissao);
            entrada.fullCheckCriarEmpregado();
            //Cria novo empregado comissionado
            e = new EmpregadoComissionado(id, nome, endereco, tipo, salario,
                    comissao);
            //Adiciona ao xml
            addEmpregadoXML(e);
            //Incrementa id e retorna
            String idAtual = getIdEmpregado();
            idEmpregado++;
            return idAtual;
        }

        public String getAtributoEmpregado(String emp, String atributo) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, AtributoNaoExisteException, EmpregadoNaoHoristaException, EmpregadoNaoComissionadoException, EmpregadoNaoRecebeBancoException, EmpregadoNaoSindicalizadoException {
            //Acessa lista de empregados
            ListaEmpregados list = readXML();
            if (list == null) list = new ListaEmpregados();
            //Declara empregado
            Empregado e;
            //Tratamento de entrada
            TratamentoEntrada entrada = new TratamentoEntrada(emp);
            entrada.fullCheckGetAtributo(atributo);

            //Procura por empregado
            e = list.searchEmpregado(emp);

            return switch (atributo) {
                case "nome" -> e.getNome();
                case "endereco" -> e.getEndereco();
                case "tipo" -> e.getTipo();
                case "salario" -> formatDoubleOutput(e.getSalario());
                case "sindicalizado" -> Boolean.toString(e.isSindicalizado());
                case "metodoPagamento" -> e.getMetodoPagamento();
                case "comissao" -> {
                    if (!e.getTipo().equals("comissionado")) throw new EmpregadoNaoComissionadoException();
                    yield formatDoubleOutput(((EmpregadoComissionado) e).getTaxaDeComissao());
                }
                case "banco" -> {
                    if (!e.getMetodoPagamento().equals("banco")) throw new EmpregadoNaoRecebeBancoException();
                    yield ((Banco) e.getObjMetodoPagamento()).getBanco();
                }
                case "idSindicato" -> {
                    if (!e.isSindicalizado()) throw new EmpregadoNaoSindicalizadoException();
                    yield e.getSindicato().getIdMembro();
                }
                case "agencia" -> {
                    if (!e.getMetodoPagamento().equals("banco")) throw new EmpregadoNaoRecebeBancoException();
                    yield ((Banco) e.getObjMetodoPagamento()).getAgencia();
                }
                case "contaCorrente" -> {
                    if (!e.getMetodoPagamento().equals("banco")) throw new EmpregadoNaoRecebeBancoException();
                    yield ((Banco) e.getObjMetodoPagamento()).getContaCorrente();
                }
                case "taxaSindical" -> {
                    if (!e.isSindicalizado()) throw new EmpregadoNaoSindicalizadoException();
                    yield formatDoubleOutput(e.getSindicato().getTaxaSindical());
                }
                default -> "";
            };
        }

        public void alteraEmpregado(String emp, String atributo, String valor1) throws EmpregadoNaoExisteException, EmpregadoNaoHoristaException, EmpregadoNaoComissionadoException, ValorTrueOuFalseException, MetodoPagamentoInvalidoException, ComissaoNaoPodeSerNulaException, ComissaoDeveSerNumericaException, ComissaoDeveSerNaoNegativaException, SalarioDeveSerNaoNegativoException, SalarioNaoPodeSerNuloException, SalarioDeveSerNumericoException, TipoNaoAplicavelException, TipoInvalidoException, EnderecoNaoPodeSerNuloException, NomeNaoPodeSerNuloException, AtributoNaoExisteException, IdEmpregadoNaoPodeSerNuloException {
            ListaEmpregados empregados = readXML();

            TratamentoEntrada entrada = new TratamentoEntrada(emp);
            entrada.checkIdEmpregado();

            assert empregados != null;
            Empregado e = empregados.searchEmpregado(emp);

            entrada = new TratamentoEntrada(emp, e.getTipo());

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
                    e.setSalario(valor1);
                    break;
                case "metodoPagamento":
                    entrada.checkMetodoPagamento(valor1);
                    e.setMetodoPagamento(valor1);
                    break;
                case "comissao":
                    entrada.checkTipoEmpregado("comissionado");
                    entrada.checkComissao(valor1);
                    ((EmpregadoComissionado) e).setTaxaDeComissao(valor1);
                    break;
                case "sindicalizado":
                    if (valor1.equals("true") || valor1.equals("false")) e.setSindicalizado(valor1);
                    else throw new ValorTrueOuFalseException();
                    break;
                default:
                    throw new AtributoNaoExisteException();
            }
            empregados.set(emp, e);
            writeToXML(empregados);
        }

        public void alteraEmpregado(String emp, String atributo, boolean valor1, String idSindicato, String taxaSindical) throws EmpregadoNaoExisteException, HaOutroEmpregadoIdMembroException, TaxaSindicalNaoNulaException, TaxaSindicalNaoNegativaException, TaxaSindicalNumericaException, IdSindicatoNaoNuloException {

            TratamentoEntrada entrada = new TratamentoEntrada();
            entrada.checkAlteraAtributoSindical(taxaSindical, idSindicato);

            ListaEmpregados empregados = readXML();

            assert empregados != null;

            Empregado e = empregados.searchEmpregado(emp);
            empregados.checkMembroId(idSindicato);

            if (valor1)
            {
                e.setSindicalizado(true, idSindicato, taxaSindical);
            }
            empregados.set(emp, e);

            writeToXML(empregados);
        }

        public void alteraEmpregado(String emp, String atributo, String valor, String comissao) throws EmpregadoNaoExisteException, NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException {

            ListaEmpregados empregados = readXML();

            assert empregados != null;
            Empregado e = empregados.searchEmpregado(emp);

            if (valor.equals("comissionado"))
            {
                if (e.getTipo().equals("horista") || e.getTipo().equals("assalariado"))
                {

                    e = new EmpregadoComissionado(e.getId(), e.getNome(), e.getEndereco(), valor,
                            Double.toString(e.getSalario()), comissao);
                }
                else
                {
                    e.setTipo("comissionado");
                    ((EmpregadoComissionado) e).setTaxaDeComissao(comissao);
                }
            }
            else if (valor.equals("horista"))
            {
                if (e.getTipo().equals("comissionado") || e.getTipo().equals("assalariado"))
                {
                    e = new EmpregadoHorista(e.getId(), e.getNome(), e.getEndereco(), valor, comissao);
                }
                else
                {
                    e.setTipo("horista");
                    e.setSalario(comissao);
                }
            }

            empregados.set(emp, e);

            writeToXML(empregados);
        }


        public void alteraEmpregado(String emp, String atributo, String valor1, String banco, String agencia,
                                    String contaCorrente) throws EmpregadoNaoExisteException, AgenciaNaoNuloException, ContaCorrenteNaoNuloException, BancoNaoNuloException {
            ListaEmpregados empregados = readXML();

            assert empregados != null;
            Empregado e = empregados.searchEmpregado(emp);

            TratamentoEntrada entrada = new TratamentoEntrada();
            entrada.checkAlteraAtributosBanco(banco, agencia, contaCorrente);

            e.setMetodoPagamento(banco, agencia, contaCorrente);

            empregados.set(emp, e);
            writeToXML(empregados);
        }



        public String getEmpregadoPorNome(String nome, int indice) throws NaoHaEmpregadoComEsseNomeException {
            ListaEmpregados empregados = readXML();
            assert empregados != null;
            return empregados.searchEmpregadoByName(nome, indice);
        }

        public void removerEmpregado (String emp) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException {

            TratamentoEntrada entrada = new TratamentoEntrada(emp);
            entrada.checkIdEmpregado();

            ListaEmpregados empregados = readXML();
            assert empregados != null;
            empregados.remove(emp);

            writeToXML(empregados);
        }

        public void lancaCartao(String emp, String data, String horas) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, EmpregadoNaoHoristaException, HorasDevemSerPositivasException, DataInvalidaException, EmpregadoNaoComissionadoException {

            TratamentoEntrada entrada = new TratamentoEntrada(emp);
            entrada.checkIdEmpregado();

            ListaEmpregados empregados = readXML();
            assert empregados != null;
            Empregado e = empregados.searchEmpregado(emp);

            entrada = new TratamentoEntrada(e.getId(), e.getTipo());
            entrada.checkTipoEmpregado("horista");
            entrada.checkHoras(horas);

            if (e.getTipo().equals("horista"))
            {
                ((EmpregadoHorista) e).addNewPonto(data, horas);
                empregados.set(emp, e);
            }
            writeToXML(empregados);
        }

        public void lancaVenda(String emp, String data, String valor) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, DataInvalidaException, EmpregadoNaoHoristaException, EmpregadoNaoComissionadoException, ValorDeveSerPositivoException {

            TratamentoEntrada entrada = new TratamentoEntrada(emp);
            entrada.checkIdEmpregado();
            entrada.checkValorVenda(valor);

            ListaEmpregados empregados = readXML();
            assert empregados != null;
            Empregado e = empregados.searchEmpregado(emp);

            entrada = new TratamentoEntrada(e.getId(), e.getTipo());
            entrada.checkTipoEmpregado("comissionado");

            if (e.getTipo().equals("comissionado"))
            {
                ((EmpregadoComissionado) e).addNewVenda(data, valor);
                empregados.set(emp, e);
            }

            writeToXML(empregados);
        }

        public void lancaTaxaServico(String membro, String data, String valor) throws DataInvalidaException, MembroNaoExisteException, IdMembroNaoPodeSerNulaException, ValorDeveSerPositivoException {

            TratamentoEntrada entrada = new TratamentoEntrada(membro);
            entrada.checkIdMembro();
            entrada.checkValorVenda(valor);

            ListaEmpregados empregados = readXML();
            assert empregados != null;
            Empregado e = empregados.searchEmpregadoMembro(membro);

            if (e.isSindicalizado())
            {
                e.addNewTaxaServico(data, valor);
                empregados.set(e.getId(), e);
            }
            writeToXML(empregados);
        }

        private Empregado initGetServico(String emp, String tipo) throws IdEmpregadoNaoPodeSerNuloException, EmpregadoNaoExisteException, EmpregadoNaoHoristaException, EmpregadoNaoComissionadoException {
            TratamentoEntrada entrada = new TratamentoEntrada(emp);
            entrada.checkIdEmpregado();


            ListaEmpregados empregados = readXML();
            assert empregados != null;
            Empregado e = empregados.searchEmpregado(emp);


            entrada = new TratamentoEntrada(e.getId(), e.getTipo());
            entrada.checkTipoEmpregado(tipo);
            return e;
        }

        public String getVendasRealizadas(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException, EmpregadoNaoHoristaException, EmpregadoNaoComissionadoException {
            String tipo = "comissionado";

            Empregado e = initGetServico(emp, tipo);

            double totalVendas = 0;
            if (e.getTipo().equals(tipo))
            {
                totalVendas = ((EmpregadoComissionado) e).getVendas(dataInicial,
                        dataFinal);
            }

            return formatDoubleOutput(totalVendas);
        }
        public String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, EmpregadoNaoHoristaException, DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException, EmpregadoNaoComissionadoException {

            String tipo = "horista";

            Empregado e = initGetServico(emp, tipo);

            double totalHoras = 0;

            if (e.getTipo().equals(tipo))
                totalHoras = ((EmpregadoHorista) e).getHoras(dataInicial, dataFinal, false);

            return (totalHoras % 1 !=0 && totalHoras!=0? String.format("%,.1f", totalHoras):
                    Integer.toString((int)totalHoras));
        }
        public String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, EmpregadoNaoHoristaException, DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException, EmpregadoNaoComissionadoException {

            String tipo = "horista";

            Empregado e = initGetServico(emp, tipo);

            double totalHoras = 0;

            if (e.getTipo().equals(tipo))
                totalHoras = ((EmpregadoHorista) e).getHoras(dataInicial, dataFinal, true);

            return (totalHoras % 1 !=0 && totalHoras!=0? String.format("%,.1f", totalHoras):
                    Integer.toString((int)totalHoras));
        }

        public String getTaxasServico(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, DataInicialInvalidaException, DataFinalInvalidaException, DataInicialPosteriorFinalException, EmpregadoNaoSindicalizadoException {
            TratamentoEntrada entrada = new TratamentoEntrada(emp);
            entrada.checkIdEmpregado();

            double totalTaxas;

            ListaEmpregados empregados = readXML();
            assert empregados != null;
            Empregado e = empregados.searchEmpregado(emp);

            if (e.isSindicalizado())
                totalTaxas = e.getTaxasServico(dataInicial, dataFinal);
            else {
                throw new EmpregadoNaoSindicalizadoException();
            }


            return formatDoubleOutput(totalTaxas);
        }




        public String totalFolha(String data) throws DataInvalidaException, DataInicialPosteriorFinalException {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate dataF;
            LocalDate dataInicialComissionado;
            try {
                dataF = LocalDate.parse(data, formatter);
                dataInicialComissionado = LocalDate.parse("1/1/2005", formatter);
            }catch (DateTimeParseException e)
            {
                throw new DataInvalidaException();
            }
            ListaEmpregados empregados = (ListaEmpregados) xstream.fromXML(persistence.readXMLFile());
            double total = 0;
            for (Empregado e: empregados.getList())
            {
                switch (e.getTipo()) {
                    case "horista" -> {
                        EmpregadoHorista eh = (EmpregadoHorista) e;
                        if (dataF.getDayOfWeek() == DayOfWeek.FRIDAY) {
                            total += eh.getHoras(dataF.minusDays(7), dataF, false) * eh.getSalario();
                            total += eh.getHoras(dataF.minusDays(7), dataF, true) * eh.getSalario() * 1.5;
                        }
                    }
                    case "comissionado" -> {
                        EmpregadoComissionado ec = (EmpregadoComissionado) e;
                        if (dataF.getDayOfWeek() == DayOfWeek.FRIDAY) {
                            if ((ChronoUnit.DAYS.between(dataInicialComissionado, dataF) + 1) % 14 == 0) {
                                total += Math.floor(ec.getVendas(dataF.minusDays(13), dataF) * ec.getTaxaDeComissao() * 100) / 100d;
                                total += Math.floor(ec.getSalario() * 24 / 52 * 100) / 100d;
                            }
                        }
                    }
                    case "assalariado" -> {
                        EmpregadoAssalariado ea = (EmpregadoAssalariado) e;
                        if (dataF.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth() == dataF.getDayOfMonth()) {
                            total += ea.getSalario();
                        }
                    }
                }
            }
            return String.format("%,.2f",total).replace(".", "");
        }

        public void rodaFolha(String data, String saida) {
            /*ListaEmpregados empregados = (ListaEmpregados) xstream.fromXML(persistence.readXMLFile());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate dataF;
            LocalDate dataInicialComissionado;
            try {
                dataF = LocalDate.parse(data, formatter);
                dataInicialComissionado = LocalDate.parse("1/1/2005", formatter);
            }catch (DateTimeParseException e)
            {
                throw new DataInvalidaException();
            }
            Persistence persistence = new Persistence(saida);
            persistence.createFile();
            persistence.appendToFile(String.format("FOLHA DE PAGAMENTO DO DIA %s", dataF) + "\n" +
                    "====================================" + "\n\n");
            persistence.readWriteToFile("folhaPagamentoCabecalhos\\horista.txt");
            for (Empregado e: empregados.getList())
            {
                if (e.getTipo().equals("horista"))
                {
                    String info = ((EmpregadoHorista) e).getInfo(dataF);
                    persistence.appendToFile(info);
                }
            }
            persistence.readWriteToFile("folhaPagamentoCabecalhos\\assalariado.txt");
            persistence.readWriteToFile("folhaPagamentoCabecalhos\\comissionado.txt");
            persistence.appendToFile(String.format("TOTAL FOLHA: %s", totalFolha(data)));*/
        }
    }
