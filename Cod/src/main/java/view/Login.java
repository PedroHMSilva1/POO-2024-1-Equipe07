package view;

import Controller.BancoDeDados;
import static Controller.BancoDeDados.recuperarUsuario;
import Model.Beans.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends JFrame {

    private JTextField textFieldEmail;
    private JPasswordField textFieldSenha;
    private static final Logger LOGGER = Logger.getLogger(Login.class.getName());

    public Login() {
        // Configura as propriedades da janela
        setTitle("Login");
        setSize(260, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Cria os componentes
        JLabel labelEmail = new JLabel("Email:");
        textFieldEmail = new JTextField(15);
        JLabel labelSenha = new JLabel("Senha:");
        textFieldSenha = new JPasswordField(15);
        JButton buttonLogin = new JButton("Login");
        JButton buttonVoltar = new JButton("Voltar");

        // Define o layout
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Adiciona os componentes à janela
        add(labelEmail);
        add(textFieldEmail);
        add(labelSenha);
        add(textFieldSenha);
        add(buttonLogin);
        add(buttonVoltar);

        // Adiciona o ouvinte de eventos ao botão
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarLogin();
            }
        });

        buttonVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> BancoDeDados.main(null));
            }
    });
    }

   private void verificarLogin() {
    String email = textFieldEmail.getText();
    String senha = new String(textFieldSenha.getPassword());

    if (email.isEmpty() || senha.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        int userID = BancoDeDados.verificarLogin(email, senha);
        if (userID != -1) {
            // Recupera o usuário do banco de dados usando o ID
            Usuario usuario = BancoDeDados.recuperarUsuario(userID);

            JOptionPane.showMessageDialog(this, "Login efetuado com sucesso!");
            dispose(); // Fecha a janela de login
            // Abre o MainFrame
            SwingUtilities.invokeLater(() -> {
                try {
                    MainFrame mainFrame = new MainFrame(userID);
                    mainFrame.setVisible(true);
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado ou senha incorreta.", "Erro",
                    JOptionPane.ERROR_MESSAGE);

            // Volta para a tela de escolha
            dispose(); // Fecha a janela de login
            // Abre a tela de escolha
            SwingUtilities.invokeLater(() -> {
                BancoDeDados.main(new String[]{});
            });
        }
    } catch (SQLException ex) {
        LOGGER.log(Level.SEVERE, "Erro ao verificar login", ex);
        JOptionPane.showMessageDialog(this, "Erro ao verificar login: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}


}
