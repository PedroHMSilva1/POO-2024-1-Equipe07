package view;

import Controller.BancoDeDados;
import Model.Beans.Usuario;

import javax.swing.*;
import java.awt.*;

public class Perfil extends JFrame {

    public Perfil(Usuario usuario) {
        setTitle("Perfil do Usuário");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Nome: " + usuario.getNome());
        JLabel emailLabel = new JLabel("Email: " + usuario.getEmail());
        JLabel phoneLabel = new JLabel("Telefone: " + usuario.getTelefone());
        JLabel typeLabel = new JLabel("Tipo: " + (usuario.isOrganizador() ? "Organizador" : "Cliente"));

        panel.add(nameLabel);
        panel.add(emailLabel);
        panel.add(phoneLabel);
        panel.add(typeLabel);

        add(panel);
    }
}
