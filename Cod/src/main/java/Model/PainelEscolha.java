package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PainelEscolha extends JPanel {

    public PainelEscolha() {
        setLayout(new BorderLayout());

        // Labels
        JLabel titleLabel = new JLabel("Escolha o Papel");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Botões de escolha
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        JButton clienteButton = new JButton("Cliente");
        JButton organizadorButton = new JButton("Organizador");

        // Adicionando ActionListener para o botão "Cliente"
        clienteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //link de classes
                SwingUtilities.invokeLater(() -> new Cadastro());
            }
        });

        // Adicionando ActionListener para o botão "Organizador"
        organizadorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //link de classes
                SwingUtilities.invokeLater(() -> new Cadastro());
            }
        });

        buttonPanel.add(clienteButton);
        buttonPanel.add(organizadorButton);
        add(buttonPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new PainelEscolha());
            frame.setSize(300, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
