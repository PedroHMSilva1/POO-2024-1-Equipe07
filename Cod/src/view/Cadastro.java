package view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cadastro extends JFrame {
    private JTextField nomeField, emailField, telefoneField;
    private JPasswordField senhaField;
    private JFormattedTextField dataNascimentoField;
    private JCheckBox checkfield;

    public Cadastro() {
        super("Cadastro");

        JPanel panel = new JPanel(new GridLayout(7, 5));
        panel.add(new JLabel("Nome:"));
        nomeField = new JTextField(20);
        panel.add(nomeField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        panel.add(emailField);

        panel.add(new JLabel("Senha:"));
        senhaField = new JPasswordField(10);
        panel.add(senhaField);

        panel.add(new JLabel("Telefone:"));
        telefoneField = new JTextField(20);
        panel.add(telefoneField);

        panel.add(new JLabel("Data de Nascimento (dd/mm/aaaa):"));
        MaskFormatter formatter = createFormatter("##/##/####");
        dataNascimentoField = new JFormattedTextField(formatter);
        panel.add(dataNascimentoField);

        panel.add(new JLabel("Organizador de Evento:"));
        checkfield = new JCheckBox();
        panel.add(checkfield);
        
        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmarCadastro();
            }
        });
        panel.add(cadastrarButton);

        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private MaskFormatter createFormatter(String format) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(format);
        } catch (java.text.ParseException exc) {
            System.err.println("Formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

    private void confirmarCadastro() {
        JOptionPane.showMessageDialog(this, "Cadastro confirmado!");
    }

    public String getNomeFieldText() {
        return nomeField.getText();
    }

    public void setNomeFieldText(String text) {
        nomeField.setText(text);
    }

    public String getEmailFieldText() {
        return emailField.getText();
    }

    public void setEmailFieldText(String text) {
        emailField.setText(text);
    }

    public String getTelefoneFieldText() {
        return telefoneField.getText();
    }

    public void setTelefoneFieldText(String text) {
        telefoneField.setText(text);
    }

    public String getSenhaFieldText() {
        return new String(senhaField.getPassword());
    }

    public void setSenhaFieldText(String text) {
        senhaField.setText(text);
    }

    public String getDataNascimentoFieldText() {
        return dataNascimentoField.getText();
    }

    public void setDataNascimentoFieldText(String text) {
        dataNascimentoField.setText(text);
    }

    public boolean isOrganizadorSelected() {
        return checkfield.isSelected();
    }

    public void setOrganizadorSelected(boolean selected) {
        checkfield.setSelected(selected);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Cadastro();
            }
        });
    }
}
