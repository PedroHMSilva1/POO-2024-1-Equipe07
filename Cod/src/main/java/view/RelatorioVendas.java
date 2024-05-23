package view;

import Controller.BancoDeDados;
import Model.Beans.Compra;
import Model.Beans.Evento;
import Model.Beans.Usuario;
import Model.Beans.VendaEvento;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RelatorioVendas extends JDialog {

    public RelatorioVendas(JFrame parentFrame, Usuario usuarioLogado) {
        super(parentFrame, "Relatório de Vendas", true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        try {
            // Recuperar as vendas por evento
            Map<Integer, VendaEvento> vendasPorEvento = BancoDeDados.recuperarVendasPorEvento();

            JTextArea relatorioTextArea = new JTextArea();
            relatorioTextArea.setEditable(false);
            relatorioTextArea.setFont(new Font("Arial", Font.PLAIN, 14));

            // Totais
            int totalIngressos = 0;
            double totalValorPago = 0.0;

            // Exibir os resultados no relatório
            for (VendaEvento vendaEvento : vendasPorEvento.values()) {
                String nomeEvento = vendaEvento.getNomeEvento();
                int quantidade = vendaEvento.getQuantidadeTotal();
                double valorTotal = vendaEvento.getValorTotal();

                totalIngressos += quantidade;
                totalValorPago += valorTotal;

                relatorioTextArea.append("Evento: " + nomeEvento + "\n");
                relatorioTextArea.append("Quantidade de Ingressos Vendidos: " + quantidade + "\n");
                relatorioTextArea.append("Valor Total Pago: R$" + valorTotal + "\n");
                relatorioTextArea.append("------------------------------------\n");
            }

            // Exibir os totais
            relatorioTextArea.append("Total de Ingressos Vendidos na Plataforma: " + totalIngressos + "\n");
            relatorioTextArea.append("Total do Valor Pago na Plataforma: R$" + totalValorPago + "\n");

            JScrollPane scrollPane = new JScrollPane(relatorioTextArea);
            panel.add(scrollPane, BorderLayout.CENTER);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao recuperar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        getContentPane().add(panel);
        setSize(600, 400);
        setLocationRelativeTo(parentFrame);
    }
}
