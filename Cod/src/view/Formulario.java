package view;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Formulario extends JFrame {
    private JTextField textFieldNome;
    private JTextField textFieldEmail;
    private JTextField textFieldTel;
    private boolean isOrganizador;

    public Formulario() {
        // Configura as propriedades da janela
        setTitle("Formulário Básico");
        setSize(260, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Impede o redimensionamento da janela

        // Cria os componentes
        JLabel labelNome = new JLabel("Nome:");
        textFieldNome = new JTextField(15);
        JLabel labelEmail = new JLabel("Email:");
        textFieldEmail = new JTextField(15);
        JLabel labelTel = new JLabel("Tel:");
        textFieldTel = new JTextField(15);
        JButton button = new JButton("Enviar");

        // Cria os botões de rádio
        JRadioButton radioOrganizador = new JRadioButton("Organizador");
        JRadioButton radioCliente = new JRadioButton("Cliente");

        // Agrupa os botões de rádio
        ButtonGroup group = new ButtonGroup();
        group.add(radioOrganizador);
        group.add(radioCliente);

        // Define o layout com alinhamento vertical
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 10 pixels de espaço entre os componentes

        // Adiciona os componentes à janela
        add(labelNome);
        add(textFieldNome);
        add(labelEmail);
        add(textFieldEmail);
        add(labelTel);
        add(textFieldTel);
        add(radioOrganizador);
        add(radioCliente);
        add(button);

        // Definindo o botão "Cliente" como selecionado por padrão
        radioCliente.setSelected(true);

        // Adiciona o ouvinte de eventos ao botão
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarFormulario();
            }
        });

        // Adiciona o ouvinte de eventos aos botões de rádio
        radioOrganizador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isOrganizador = true;
            }
        });

        radioCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isOrganizador = false;
            }
        });
    }

    private void enviarFormulario() {
        // Verifica se todos os campos estão preenchidos
        if (textFieldNome.getText().isEmpty() || textFieldEmail.getText().isEmpty()
                || textFieldTel.getText().isEmpty()) {
            // Exibe mensagem de erro se algum campo estiver vazio
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            // Todos os campos estão preenchidos, então imprime os valores no console
            System.out.println("Nome: " + textFieldNome.getText());
            System.out.println("Email: " + textFieldEmail.getText());
            System.out.println("Tel: " + textFieldTel.getText());
            System.out.println("Tipo: " + (isOrganizador ? "Organizador" : "Cliente"));

            // Exibe mensagem de sucesso
            JOptionPane.showMessageDialog(this, "Cadastrado com sucesso!");

            // Limpa os campos de entrada
            textFieldNome.setText("");
            textFieldEmail.setText("");
            textFieldTel.setText("");
            isOrganizador = false;
        }
    }

    public static void main(String[] args) {
        Formulario frame = new Formulario();
        frame.setLocationRelativeTo(null); // Centraliza a janela na tela
        frame.setVisible(true);
    }
}
