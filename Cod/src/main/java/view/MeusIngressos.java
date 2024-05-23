package view;

import Controller.BancoDeDados;
import Model.Beans.Compra;
import Model.Beans.Evento;
import Model.Beans.Usuario;
import Model.DAO.IngressoUsuarioDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class MeusIngressos extends JDialog {

    public MeusIngressos(JFrame parentFrame, Usuario usuarioLogado) {
        super(parentFrame, "Meus Ingressos", true);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10)); // Alterado para uma coluna para acomodar todas as informações
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        try {
            IngressoUsuarioDAO ingressoUsuarioDAO = new IngressoUsuarioDAO();
            ArrayList<Compra> compras = ingressoUsuarioDAO.recuperarComprasPorUsuario(usuarioLogado);

            for (Compra compra : compras) {
                JPanel compraPanel = new JPanel(new GridLayout(5, 1)); // Painel para cada compra com cinco linhas
                compraPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // Recuperar o nome do evento e a localização com base no ID do evento
                Evento evento = BancoDeDados.recuperarEventoPorId(compra.getEventoId());
                String nomeEvento = evento.getTitulo(); // Supondo que existe um método getNome() na classe Evento
                String localizacao = evento.getLocalizacao(); // Supondo que existe um método getLocalizacao() na classe Evento

                JLabel eventoLabel = new JLabel("Evento: " + nomeEvento); // Exibir o nome do evento
                JLabel localizacaoLabel = new JLabel("Localização: " + localizacao); // Exibir a localização
                JLabel quantidadeLabel = new JLabel("Quantidade: " + compra.getQuantidade());
                JLabel valorPagoLabel = new JLabel("Valor Pago: " + compra.getValorPago());
                JLabel dataHoraCompraLabel = new JLabel("Data/Hora da Compra: " + compra.getDataHoraCompra());

                compraPanel.add(eventoLabel);
                compraPanel.add(localizacaoLabel);
                compraPanel.add(quantidadeLabel);
                compraPanel.add(valorPagoLabel);
                compraPanel.add(dataHoraCompraLabel);

                panel.add(compraPanel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao recuperar ingressos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        getContentPane().add(new JScrollPane(panel));
        setSize(800, 400); // Ajustando o tamanho do painel conforme necessário
        setLocationRelativeTo(parentFrame);
    }
}


