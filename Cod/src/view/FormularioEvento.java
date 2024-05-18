package view;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class FormularioEvento extends JFrame {
    private JTextField textFieldTitulo;
    private JFormattedTextField formattedTextFieldDataHora;
    private JTextField textFieldLocalizacao;
    private JTextArea textAreaDescricao; // Alteração para JTextArea
    private JTextField textFieldCapacidade;
    private JTextField textFieldIngressos;

    public FormularioEvento() {
        // Configura as propriedades da janela
        setTitle("Formulário de Evento");
        setSize(270, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Impede o redimensionamento da janela

        // Cria os componentes
        JLabel labelTitulo = new JLabel("Título do Evento:");
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
        JLabel labelDescricao = new JLabel("Descrição do Evento:");
        textAreaDescricao = new JTextArea(5, 20); // Define o tamanho inicial para 5 linhas e 20 colunas
        textAreaDescricao.setLineWrap(true); // Permite que o texto quebre automaticamente para a próxima linha
        JScrollPane scrollPane = new JScrollPane(textAreaDescricao); // Adiciona uma barra de rolagem
        JLabel labelCapacidade = new JLabel("Capacidade:");
        textFieldCapacidade = new JTextField(20);
        JLabel labelIngressos = new JLabel("Ingressos:");
        textFieldIngressos = new JTextField(20);
        JLabel labelVazio = new JLabel("      ");
        JButton button = new JButton("Enviar");

        // Define o layout com alinhamento vertical
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 10 pixels de espaço entre os componentes

        // Adiciona os componentes à janela
        add(labelTitulo);
        add(textFieldTitulo);
        add(labelDataHora);
        add(formattedTextFieldDataHora);
        add(labelLocalizacao);
        add(textFieldLocalizacao);
        add(labelDescricao);
        add(scrollPane); // Adiciona o scrollPane em vez do textArea diretamente
        add(labelCapacidade);
        add(textFieldCapacidade);
        add(labelIngressos);
        add(textFieldIngressos);
        add(labelVazio); // Adiciona um espaço vazio entre os componentes
        add(button);

        // Adiciona o ouvinte de eventos ao botão
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarFormulario();
            }
        });
    }

    private void enviarFormulario() {
        // Verifica se todos os campos estão preenchidos
        if (textFieldTitulo.getText().isEmpty() || formattedTextFieldDataHora.getText().isEmpty()
                || textFieldLocalizacao.getText().isEmpty() || textAreaDescricao.getText().isEmpty()
                || textFieldCapacidade.getText().isEmpty() || textFieldIngressos.getText().isEmpty()) {
            // Exibe mensagem de erro se algum campo estiver vazio
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            // Todos os campos estão preenchidos, então imprime os valores no console
            System.out.println("Título do Evento: " + textFieldTitulo.getText());
            System.out.println("Data e Hora: " + formattedTextFieldDataHora.getText());
            System.out.println("Localização: " + textFieldLocalizacao.getText());
            System.out.println("Descrição do Evento: " + textAreaDescricao.getText());
            System.out.println("Capacidade: " + textFieldCapacidade.getText());
            System.out.println("Ingressos: " + textFieldIngressos.getText());

            // Exibe mensagem de sucesso
            JOptionPane.showMessageDialog(this, "Evento cadastrado com sucesso!");

            // Limpa os campos de entrada
            textFieldTitulo.setText("");
            formattedTextFieldDataHora.setText("");
            textFieldLocalizacao.setText("");
            textAreaDescricao.setText("");
            textFieldCapacidade.setText("");
            textFieldIngressos.setText("");
        }
    }

    public static void main(String[] args) {
        FormularioEvento frame = new FormularioEvento();
        frame.setLocationRelativeTo(null); // Centraliza a janela na tela
        frame.setVisible(true);
    }
}
