package Model.Beans;

public class Evento {
    private int id;
    private String titulo;
    private String dataHora;
    private String localizacao;
    private String descricao;
    private int capacidade;
    private double valorIngressos;
    private int idOrganizador;

    public Evento(int id, String titulo, String dataHora, String localizacao, String descricao, int capacidade, double valorIngressos, int idOrganizador) {
        this.id = id;
        this.titulo = titulo;
        this.dataHora = dataHora;
        this.localizacao = localizacao;
        this.descricao = descricao;
        this.capacidade = capacidade;
        this.valorIngressos = valorIngressos;
        this.idOrganizador = idOrganizador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Evento() {
    }

    // Getters e setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public double getValorIngressos() {
        return valorIngressos;
    }

    public void setValorIngressos(double valorIngressos) {
        this.valorIngressos = valorIngressos;
    }

    public int getIdOrganizador() {
        return idOrganizador;
    }

    public void setIdOrganizador(int idOrganizador) {
        this.idOrganizador = idOrganizador;
    }

    @Override
    public String toString() {
        return "Evento{" + "id=" + id + ", titulo=" + titulo + ", dataHora=" + dataHora + ", localizacao=" + localizacao + ", descricao=" + descricao + ", capacidade=" + capacidade + ", valorIngressos=" + valorIngressos + ", idOrganizador=" + idOrganizador + '}';
    }



}
