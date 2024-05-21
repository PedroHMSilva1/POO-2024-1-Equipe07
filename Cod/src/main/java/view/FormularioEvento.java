package view;

import Model.Beans.Evento;
import Controller.BancoDeDados;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

public class FormularioEvento extends JFrame {

    private JTextField textFieldTitulo;
    private JFormattedTextField formattedTextFieldDataHora;
    private JTextField textFieldLocalizacao;
    private JTextArea textAreaDescricao;
    private JTextField textFieldCapacidade;
    private JTextField textFieldValorIngressos;
    private MainFrame mainFrame;

    public FormularioEvento(int idOrganizador, MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setTitle("Cadastrar Evento");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JLabel labelTitulo = new JLabel("Título:");
        textFieldTitulo = new JTextField(20);
        JLabel labelDataHora = new JLabel("Data e Hora (yyyy-MM-dd HH:mm):");
        try {
            MaskFormatter formatter = new MaskFormatter("####-##-## ##:##");
            formattedTextFieldDataHora = new JFormattedTextField(formatter);
            formattedTextFieldDataHora.setColumns(20);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        JLabel labelLocalizacao = new JLabel("Localização:");
        textFieldLocalizacao = new JTextField(20);
        JLabel labelDescricao = new JLabel("Descrição:");
        textAreaDescricao = new JTextArea(5, 20);
        JLabel labelCapacidade = new JLabel("Capacidade:");
        textFieldCapacidade = new JTextField(20);
        JLabel labelValorIngressos = new JLabel("Valor dos Ingressos:");
        textFieldValorIngressos = new JTextField(20);
        JButton buttonCadastrar = new JButton("Cadastrar");

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        add(labelTitulo);
        add(textFieldTitulo);
        add(labelDataHora);
        add(formattedTextFieldDataHora);
        add(labelLocalizacao);
        add(textFieldLocalizacao);
        add(labelDescricao);
        add(new JScrollPane(textAreaDescricao));
        add(labelCapacidade);
        add(textFieldCapacidade);
        add(labelValorIngressos);
        add(textFieldValorIngressos);
        add(buttonCadastrar);

        buttonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cadastrarEvento(idOrganizador);
                    mainFrame.atualizarEventos();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(FormularioEvento.this, "Erro ao cadastrar evento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void cadastrarEvento(int idOrganizador) throws SQLException {
        if (textFieldTitulo.getText().isEmpty() || formattedTextFieldDataHora.getText().isEmpty()
                || textFieldLocalizacao.getText().isEmpty() || textAreaDescricao.getText().isEmpty()
                || textFieldCapacidade.getText().isEmpty() || textFieldValorIngressos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String titulo = textFieldTitulo.getText();
        String dataHora = formattedTextFieldDataHora.getText();
        String localizacao = textFieldLocalizacao.getText();
        String descricao = textAreaDescricao.getText();
        int capacidade = Integer.parseInt(textFieldCapacidade.getText());
        double valorIngressos = Double.parseDouble(textFieldValorIngressos.getText());

        Evento evento = new Evento(0, titulo, dataHora, localizacao, descricao, capacidade, valorIngressos, idOrganizador);
        BancoDeDados.cadastrarEvento(evento);

        JOptionPane.showMessageDialog(this, "Evento cadastrado com sucesso!");
        dispose();
    }
}
