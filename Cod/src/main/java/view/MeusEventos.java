package view;

import Model.Beans.Evento;
import Model.Beans.Usuario;
import Model.DAO.BancoDeDados;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class MeusEventos extends JFrame {

    private Usuario usuarioLogado;
    private JPanel eventsPanel;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public void limparUsuarioLogado() {
        this.usuarioLogado = null;
    }

    public MeusEventos(Usuario usuarioLogado, int userID) throws SQLException {
        this.usuarioLogado = BancoDeDados.recuperarUsuario(userID);

        setTitle("Event Master");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.3);
        splitPane.setPreferredSize(new Dimension(440, getHeight()));

        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBackground(Color.LIGHT_GRAY);

        ImageIcon profileImageIcon = new ImageIcon("Cod/src/main/java/Assets/Perfil.png");
        Image profileImage = profileImageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel profileImageLabel = new JLabel(new ImageIcon(profileImage));
        profileImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profileImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        profilePanel.add(profileImageLabel, BorderLayout.CENTER);

        JLabel usernameLabel = new JLabel(usuarioLogado.getNome());
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profilePanel.add(usernameLabel, BorderLayout.SOUTH);

        String[] menuItems = {"Profile", "Voltar"};
        JList<String> menuList = new JList<>(menuItems);
        JScrollPane menuScrollPane = new JScrollPane(menuList);
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.add(menuScrollPane);

        menuList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedMenuItem = menuList.getSelectedValue();
                if (selectedMenuItem != null) {
                    switch (selectedMenuItem) {
                        case "Voltar":
                            dispose();
                            MainFrame mainFrame = null;
                            try {
                                mainFrame = new MainFrame(userID);
                            } catch (SQLException ex) {
                                Logger.getLogger(MeusEventos.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            mainFrame.setVisible(true);
                            break;

                        case "Profile":
                            exibirPerfil(usuarioLogado);
                            break;
                    }
                }
            }
        });

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(profilePanel, BorderLayout.NORTH);
        leftPanel.add(menuPanel, BorderLayout.CENTER);

        splitPane.setLeftComponent(leftPanel);

        JPanel mainContentPanel = new JPanel(new BorderLayout());
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));

        JScrollPane eventsScrollPane = new JScrollPane(eventsPanel);
        mainContentPanel.add(eventsScrollPane, BorderLayout.CENTER);

        splitPane.setRightComponent(mainContentPanel);

        add(splitPane, BorderLayout.CENTER);

        setSize(1200, 1000);
        setVisible(true);

        atualizarEventos();
    }

    void atualizarEventos() throws SQLException {
        eventsPanel.removeAll();
        ArrayList<Evento> eventos = BancoDeDados.recuperarEventos();
        for (Evento evento : eventos) {
            JPanel eventPanel = new JPanel(new BorderLayout());
            eventPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel titleLabel = new JLabel("Título: " + evento.getTitulo());
            JLabel dateTimeLabel = new JLabel("Data e Hora: " + evento.getDataHora());
            JLabel locationLabel = new JLabel("Localização: " + evento.getLocalizacao());

            Usuario organizador = BancoDeDados.recuperarUsuario(evento.getIdOrganizador());
            JLabel organizerLabel = new JLabel("Organizador: " + organizador.getNome());

            JPanel infoPanel = new JPanel(new GridLayout(4, 1));
            infoPanel.add(titleLabel);
            infoPanel.add(dateTimeLabel);
            infoPanel.add(locationLabel);
            infoPanel.add(organizerLabel);

            eventPanel.add(infoPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());

            JButton detailsButton = new JButton("Excluir Evento");
            detailsButton.addActionListener(e -> {
                // Implementação da exclusão de evento
            });
            buttonPanel.add(detailsButton);

            JButton buyTicketButton = new JButton("Alterar Evento");
            buyTicketButton.addActionListener(e -> {
                // Implementação da alteração de evento
            });
            buttonPanel.add(buyTicketButton);

            eventPanel.add(buttonPanel, BorderLayout.SOUTH);

            eventsPanel.add(eventPanel);
        }
        eventsPanel.revalidate();
        eventsPanel.repaint();
    }

    private void exibirPerfil(Usuario usuario) {
        Perfil janelaPerfil = new Perfil(usuarioLogado);
        janelaPerfil.setVisible(true);
    }
}
