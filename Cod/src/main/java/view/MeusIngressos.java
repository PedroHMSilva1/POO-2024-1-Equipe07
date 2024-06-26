package view;

import Model.Beans.Compra;
import Model.Beans.Evento;
import Model.Beans.Usuario;
import Model.Dao.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;

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
                String dataEvento = evento.getDataHora();

                JLabel eventoLabel = new JLabel("Evento: " + nomeEvento); // Exibir o nome do evento
                JLabel dataEventoLabel = new JLabel("Data do Evento: " + dataEvento);
                JLabel quantidadeLabel = new JLabel("Quantidade: " + compra.getQuantidade());
                JLabel valorPagoLabel = new JLabel("Valor Pago: " + compra.getValorPago());
                JLabel localizacaoLabel = new JLabel("Localização: " + localizacao + "        "); // Exibir a localização, espaço colocado como "margem" para as outras informações
                JLabel nomeCompradorLabel = new JLabel("Nome: " + compra.getNomeComprador());
                JLabel idIngressoLabel = new JLabel("Id do Ingresso: " + 1324000 + compra.getId());
                JLabel dataHoraCompraLabel = new JLabel("Data/Hora da Compra: " + compra.getDataHoraCompra());
                JLabel formaPagaLabel = new JLabel("Forma do Pagamento: " + compra.getFormaPagamento());

                compraPanel.add(eventoLabel);
                compraPanel.add(dataEventoLabel);
                compraPanel.add(localizacaoLabel);
                compraPanel.add(quantidadeLabel);                
                compraPanel.add(valorPagoLabel);
                compraPanel.add(dataHoraCompraLabel);
                compraPanel.add(nomeCompradorLabel);
                compraPanel.add(idIngressoLabel);
                compraPanel.add(formaPagaLabel);

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


