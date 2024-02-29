package br.ufal.ic.p2.wepayu.utils;

public class InfoFolha {

    private String nome;
    private int horas;
    private int extras;
    private double salBruto;
    private double desconto;
    private double salLiq;
    private String metodoPag;

    private double fixo;

    private double vendas;
    private double comissao;

    public InfoFolha(String nome, int horas, int extras, double salBruto, double desconto, double salLiq, String metodoPag) {
        this.nome = nome;
        this.horas = horas;
        this.extras = extras;
        this.salBruto = salBruto;
        this.desconto = desconto;
        this.salLiq = salLiq;
        this.metodoPag = metodoPag;
    }

    public InfoFolha(String nome, double salBruto, double desconto, double salLiq, String metodoPag) {
        this.nome = nome;
        this.salBruto = salBruto;
        this.desconto = desconto;
        this.salLiq = salLiq;
        this.metodoPag = metodoPag;
    }


    public InfoFolha(String nome, double salBruto, double desconto, double salLiq, String metodoPag, double fixo, double vendas, double comissao) {
        this.nome = nome;
        this.salBruto = salBruto;
        this.desconto = desconto;
        this.salLiq = salLiq;
        this.metodoPag = metodoPag;
        this.fixo = fixo;
        this.vendas = vendas;
        this.comissao = comissao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getExtras() {
        return extras;
    }

    public void setExtras(int extras) {
        this.extras = extras;
    }

    public double getSalBruto() {
        return salBruto;
    }

    public void setSalBruto(double salBruto) {
        this.salBruto = salBruto;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double getSalLiq() {
        return salLiq;
    }

    public void setSalLiq(double salLiq) {
        this.salLiq = salLiq;
    }

    public String getMetodoPag() {
        return metodoPag;
    }

    public void setMetodoPag(String metodoPag) {
        this.metodoPag = metodoPag;
    }

    public double getFixo() {
        return fixo;
    }

    public void setFixo(double fixo) {
        this.fixo = fixo;
    }

    public double getVendas() {
        return vendas;
    }

    public void setVendas(double vendas) {
        this.vendas = vendas;
    }

    public double getComissao() {
        return comissao;
    }

    public void setComissao(double comissao) {
        this.comissao = comissao;
    }

    public String getRealatorio(int type)
    {
        return switch (type) {
            case 1 -> String.format("%-36s %5d %5d %13.2f %9.2f %15.2f %s",
                    this.nome, horas, extras, salBruto, desconto, salLiq, metodoPag);
            case 2 -> String.format("%-48s %13.2f %9.2f %15.2f %s",
                    this.nome, salBruto, desconto, salLiq, metodoPag);
            case 3 -> String.format("%-23s %.2f %8.2f %8.2f %13.2f %9.2f %15.2f %s",
                    this.nome, fixo, vendas, comissao, salBruto, desconto, salLiq, metodoPag);
            default -> "";
        };
    }
}
