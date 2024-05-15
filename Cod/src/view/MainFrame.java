package view;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Aplicativo de Eventos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu lateral e seção de perfil do usuário
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(1.0 / 3.0); // Define a proporção para 1/3
        splitPane.setPreferredSize(new Dimension(220, getHeight())); // Define a largura mínima

        // Seção de Perfil do Usuário
        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBackground(Color.LIGHT_GRAY);

        // Círculo de perfil (imagem do usuário)
        ImageIcon profileImageIcon = new ImageIcon("src\\Assets\\Perfil.png");
        Image profileImage = profileImageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel profileImageLabel = new JLabel(new ImageIcon(profileImage));
        profileImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profileImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        profilePanel.add(profileImageLabel, BorderLayout.CENTER);

        // Nome do usuário
        JLabel usernameLabel = new JLabel("Nome do Usuário");
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profilePanel.add(usernameLabel, BorderLayout.SOUTH);

        // Menu
        String[] menuItems = { "Profile", "Eventos", "Preferências", "Meus Ingressos" };
        JList<String> menuList = new JList<>(menuItems);
        JScrollPane menuScrollPane = new JScrollPane(menuList);
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.add(menuScrollPane);

        // Adicionando os componentes ao JSplitPane
        splitPane.setTopComponent(profilePanel);
        splitPane.setBottomComponent(menuPanel);

        // Conteúdo principal (duas colunas do meio)
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Barra de pesquisa no topo
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Pesquisar");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Adicionando a barra de pesquisa no topo do painel de conteúdo
        contentPanel.add(searchPanel, BorderLayout.NORTH);

        // Conteúdo da pesquisa
        JTextArea searchResults = new JTextArea();
        searchResults.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(searchResults);

        // Adicionando o conteúdo da pesquisa abaixo da barra de pesquisa
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Coluna lateral (última coluna)
        JPanel sidePanel = new JPanel(new GridLayout(3, 1));

        // Seção de texto
        JTextArea infoText = new JTextArea();
        infoText.setText("**Título do Evento:**\n" +
                "Evento de Lançamento do Novo Produto\n\n" +
                "**Data e Hora:**\n" +
                "Quarta-Feita, 15 de Maio de 2024\n" +
                "Das 14:00 às 18:00\n\n" +
                "**Localização:**\n" +
                "Centro de Convenções XYZ\n" +
                "Endereço: Rua ABC, 123, Cidade, Estado\n\n" +
                "**Descrição do Evento:**\n" +
                "Venha participar do lançamento do nosso mais novo produto!\n\n" +
                "**Público-Alvo:**\n" +
                "Clientes atuais e potenciais, parceiros de negócios e mídia especializada.\n\n" +
                "**Ingressos e Registro:**\n" +
                "Entrada gratuita, mas é necessário registrar-se antecipadamente no nosso site.\n\n" +
                "**Palestrantes ou Apresentadores:**\n" +
                "CEO da Empresa XYZ, Gerente de Produto, Especialistas em Marketing.\n");
        infoText.setEditable(false);
        sidePanel.add(new JScrollPane(infoText));

        // Painel para a imagem do mapa
        JPanel mapPanel = new ImagePanel("src\\Assets\\mapa.jpg");
        mapPanel.add(new JLabel("Localização"));
        sidePanel.add(mapPanel);

        // Calendário
        JCalendar calendar = new JCalendar();
        sidePanel.add(calendar);

        // Adicionando os painéis ao frame
        add(splitPane, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);

        // Configuração do tamanho e visibilidade
        setSize(1200, 1000);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
