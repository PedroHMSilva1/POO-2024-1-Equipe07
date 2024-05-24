package Model.Beans;

import java.util.HashMap;
import java.util.Map;

public class VendaEvento {
    private String nomeEvento;
    private int quantidadeTotal;
    private double valorTotal;

    public VendaEvento(String nomeEvento, int quantidadeTotal, double valorTotal) {
        this.nomeEvento = nomeEvento;
        this.quantidadeTotal = quantidadeTotal;
        this.valorTotal = valorTotal;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(int quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void adicionarVenda(int quantidade, double valor) {
        this.quantidadeTotal += quantidade;
        this.valorTotal += valor;
    }
}
