package view;

import Model.Beans.Evento;
import Model.Beans.Usuario;
import Model.DAO.BancoDeDados;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class FormularioEditarEvento extends JFrame {

    private JTextField textFieldTitulo;
    private JTextField textFieldLocalizacao;
    private JTextArea textAreaDescricao;
    private Usuario usuarioLogado;
    private MeusEventos telaMeusEventos;
    private Evento evento;
    private final int userID;

    public FormularioEditarEvento(Evento evento, Usuario usuarioLogado,int userID, MeusEventos telaMeusEventos) {
        this.userID = userID;
        this.evento = evento;
        this.usuarioLogado = usuarioLogado;
        this.telaMeusEventos = telaMeusEventos;

        setTitle("Alterar Evento");
        setSize(360, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel labelTitulo = new JLabel("Título:");
        textFieldTitulo = new JTextField(evento.getTitulo(), 21);
        JLabel labelLocalizacao = new JLabel("Localização:");
        textFieldLocalizacao = new JTextField(evento.getLocalizacao(), 18);
        JLabel labelDescricao = new JLabel("Descrição:");
        textAreaDescricao = new JTextArea(evento.getDescricao(), 6, 19);
        JButton buttonSalvar = new JButton("Salvar Alterações");

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        add(labelTitulo);
        add(textFieldTitulo);
        add(labelLocalizacao);
        add(textFieldLocalizacao);
        add(labelDescricao);
        add(new JScrollPane(textAreaDescricao));
        add(buttonSalvar);

        buttonSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    salvarAlteracoes();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(FormularioEditarEvento.this, "Erro ao alterar evento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void salvarAlteracoes() throws SQLException {
        if (textFieldTitulo.getText().isEmpty() || textFieldLocalizacao.getText().isEmpty()
                || textAreaDescricao.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String titulo = textFieldTitulo.getText();
        String localizacao = textFieldLocalizacao.getText();
        String descricao = textAreaDescricao.getText();

        evento.setTitulo(titulo);
        evento.setLocalizacao(localizacao);
        evento.setDescricao(descricao);

        BancoDeDados.alteraEvento(evento);

        JOptionPane.showMessageDialog(this, "Evento alterado com sucesso! Os clientes serão notificados.");
        telaMeusEventos.atualizarEventos(usuarioLogado, userID);
        dispose();
        MeusEventos meusEventos = new MeusEventos(usuarioLogado, userID);
        meusEventos.setLocationRelativeTo(null);
        meusEventos.setVisible(true);

    }
}
