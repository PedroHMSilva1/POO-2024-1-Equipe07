package Model.Beans;

import java.time.LocalDateTime;

public class Compra {
    private int id;
    private int eventoId;
    private boolean meiaEntrada;
    private int quantidade;
    private double valorIngresso;
    private double valorPago;
    private String formaPagamento;
    private String nomeComprador;
    private LocalDateTime dataHoraCompra;

    // Construtor
    public Compra(int eventoId, boolean meiaEntrada, int quantidade, double valorIngresso, double valorPago, String formaPagamento, String nomeComprador, LocalDateTime dataHoraCompra) {
        this.eventoId = eventoId;
        this.meiaEntrada = meiaEntrada;
        this.quantidade = quantidade;
        this.valorIngresso = valorIngresso;
        this.valorPago = valorPago;
        this.formaPagamento = formaPagamento;
        this.nomeComprador = nomeComprador;
        this.dataHoraCompra = dataHoraCompra;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }

    public boolean isMeiaEntrada() {
        return meiaEntrada;
    }

    public void setMeiaEntrada(boolean meiaEntrada) {
        this.meiaEntrada = meiaEntrada;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorIngresso() {
        return valorIngresso;
    }

    public void setValorIngresso(double valorIngresso) {
        this.valorIngresso = valorIngresso;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getNomeComprador() {
        return nomeComprador;
    }

    public void setNomeComprador(String nomeComprador) {
        this.nomeComprador = nomeComprador;
    }

    public LocalDateTime getDataHoraCompra() {
        return dataHoraCompra;
    }

    public void setDataHoraCompra(LocalDateTime dataHoraCompra) {
        this.dataHoraCompra = dataHoraCompra;
    }
}
