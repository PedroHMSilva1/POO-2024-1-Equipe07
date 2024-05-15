package view;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;

public class Cadastro extends JFrame {
    private JTextField nomeField, emailField, telefoneField;
    private JPasswordField senhaField;
    private JFormattedTextField dataNascimentoField;

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
        int resposta = JOptionPane.showConfirmDialog(this, "Confirmar informações?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            cadastrar();
        } else {
        }
    }

    private void cadastrar() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());
        String telefone = telefoneField.getText();
        String dataNascimento = dataNascimentoField.getText();



        System.out.println("Nome: " + nome);
        System.out.println("Email: " + email);
        System.out.println("Senha: " + senha);
        System.out.println("Telefone: " + telefone);
        System.out.println("Data de Nascimento: " + dataNascimento);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Cadastro();
            }
        });
    }
}
