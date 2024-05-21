package view;

import Model.Beans.Usuario;
import Controller.BancoDeDados;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Formulario extends JFrame {

    private JTextField textFieldNome;
    private JTextField textFieldEmail;
    private JPasswordField passwordFieldSenha;
    private JTextField textFieldTelefone;
    private JCheckBox checkBoxOrganizador;

    public Formulario() {
        setTitle("Cadastro de Usuário");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JLabel labelNome = new JLabel("Nome:");
        textFieldNome = new JTextField(20);
        JLabel labelEmail = new JLabel("Email:");
        textFieldEmail = new JTextField(20);
        JLabel labelSenha = new JLabel("Senha:");
        passwordFieldSenha = new JPasswordField(20);
        JLabel labelTelefone = new JLabel("Telefone:");
        textFieldTelefone = new JTextField(20);
        JLabel labelOrganizador = new JLabel("Organizador:");
        checkBoxOrganizador = new JCheckBox();
        JButton buttonCadastrar = new JButton("Cadastrar");

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        add(labelNome);
        add(textFieldNome);
        add(labelEmail);
        add(textFieldEmail);
        add(labelSenha);
        add(passwordFieldSenha);
        add(labelTelefone);
        add(textFieldTelefone);
        add(labelOrganizador);
        add(checkBoxOrganizador);
        add(buttonCadastrar);

        buttonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        if (textFieldNome.getText().isEmpty() || textFieldEmail.getText().isEmpty()
                || new String(passwordFieldSenha.getPassword()).isEmpty() || textFieldTelefone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            Usuario usuario = new Usuario(
                    textFieldNome.getText(),
                    new String(passwordFieldSenha.getPassword()),
                    textFieldEmail.getText(),
                    textFieldTelefone.getText(),
                    checkBoxOrganizador.isSelected()
            );

            try {
                BancoDeDados.cadastrarUsuario(usuario);
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");

                // Fecha a tela de cadastro e reabre a tela de escolha
                dispose();
                BancoDeDados.main(new String[]{});
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
