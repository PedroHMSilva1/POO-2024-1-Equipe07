package view;

import Model.Beans.Compra;
import Model.Beans.Evento;
import Model.DAO.CompraDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class FormularioCompraIngresso extends JDialog {

    private final String nomeComprador; // Nome do usuário logado

    public FormularioCompraIngresso(JFrame parentFrame, Evento evento, String nomeComprador) {
        super(parentFrame, "Compra de Ingressos", true);
        this.nomeComprador = nomeComprador; // Atribui o nome do comprador

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelTitulo = new JLabel("Título do Evento:");
        JTextField textTitulo = new JTextField(evento.getTitulo());
        textTitulo.setEditable(false);

        JLabel labelDataEvento = new JLabel("Data do Evento:");
        JTextField textDataEvento = new JTextField(evento.getDataHora());
        textDataEvento.setEditable(false);

        JLabel labelValorIngresso = new JLabel("Valor do Ingresso:");
        JTextField textValorIngresso = new JTextField(String.valueOf(evento.getValorIngressos()));
        textValorIngresso.setEditable(false);

        JLabel labelMeia = new JLabel("Meia:");
        JCheckBox checkBoxMeia = new JCheckBox();

        JLabel labelFormaPagamento = new JLabel("Forma de Pagamento:");
        String[] formasPagamento = {"Pix", "Cartão", "Boleto"};
        JComboBox<String> comboBoxFormasPagamento = new JComboBox<>(formasPagamento);

        JLabel labelQuantidade = new JLabel("Quantidade:");
        JTextField textQuantidade = new JTextField();

        JButton botaoComprar = new JButton("Comprar");
        botaoComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int eventoId = evento.getId();
                boolean meiaEntrada = checkBoxMeia.isSelected();
                int quantidade = Integer.parseInt(textQuantidade.getText());
                double valorIngresso = Double.parseDouble(textValorIngresso.getText());
                String formaPagamento = comboBoxFormasPagamento.getSelectedItem().toString();
                LocalDateTime dataHoraCompra = LocalDateTime.now();

                // Calcular o valor do ingresso
                double valorUnitario = valorIngresso;
                if (meiaEntrada) {
                    valorUnitario /= 2; // Se for meia entrada, divide o valor pela metade
                }

                // Calcular o valor total pago
                double valorPago = valorUnitario * quantidade;

                // Mostrar janela de confirmação
                int confirmacao = JOptionPane.showConfirmDialog(FormularioCompraIngresso.this,
                        "O valor total da compra é: " + valorPago + ".\nDeseja confirmar a compra?",
                        "Confirmação de Compra", JOptionPane.YES_NO_OPTION);
                if (confirmacao == JOptionPane.YES_OPTION) {
                    // Criar o objeto Compra com os dados do formulário
                    Compra compra = new Compra(eventoId, meiaEntrada, quantidade, valorIngresso, valorPago, formaPagamento, nomeComprador, dataHoraCompra);

                    try {
                        // Chamar o método registrarCompra do CompraDAO
                        CompraDAO compraDAO = new CompraDAO();
                        compraDAO.registrarCompra(compra);
                        JOptionPane.showMessageDialog(FormularioCompraIngresso.this,
                                "Compra realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(FormularioCompraIngresso.this,
                                "Erro ao realizar compra: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        panel.add(labelTitulo);
        panel.add(textTitulo);
        panel.add(labelDataEvento);
        panel.add(textDataEvento);
        panel.add(labelValorIngresso);
        panel.add(textValorIngresso);
        panel.add(labelMeia);
        panel.add(checkBoxMeia);
        panel.add(labelFormaPagamento);
        panel.add(comboBoxFormasPagamento);
        panel.add(labelQuantidade);
        panel.add(textQuantidade);
        panel.add(new JLabel()); // Espaço vazio para alinhar o botão
        panel.add(botaoComprar);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parentFrame);
    }
}
