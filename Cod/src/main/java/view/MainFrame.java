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
import view.FormularioCompraIngresso;
import view.FormularioEvento;
import view.ImagePanel;
import view.JCalendar;
import view.MeusEventos;
import view.MeusIngressos;
import view.Perfil;

import view.RelatorioVendas;

public class MainFrame extends JFrame {

    private Usuario usuarioLogado;
    private JTextArea infoText;
    private JPanel eventsPanel;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public void limparUsuarioLogado() {
        this.usuarioLogado = null;
    }

    public MainFrame(int userID) throws SQLException {
        this.usuarioLogado = BancoDeDados.recuperarUsuario(userID);

        setTitle("Event Master");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(1.0 / 3.0);
        splitPane.setPreferredSize(new Dimension(220, getHeight()));

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

        String[] menuItems = {"Profile", "Cadastrar Eventos", "Meus Eventos", "Meus Ingressos", "Relatório de Vendas", "Logout"};
        JList<String> menuList = new JList<>(menuItems);
        JScrollPane menuScrollPane = new JScrollPane(menuList);
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.add(menuScrollPane);

        menuList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                String selectedMenuItem = menuList.getSelectedValue();
                if (selectedMenuItem != null) {
                    switch (selectedMenuItem) {
                        case "Logout":
                            realizarLogout();
                            break;
                        case "Cadastrar Eventos":
                            try {
                                verificarEExibirFormularioEvento(userID);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            break;
                        case "Meus Eventos": {
                            try {
                                exibirMeusEventos(usuarioLogado, userID);
                            } catch (SQLException ex) {
                                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;

                        case "Profile":
                            exibirPerfil(usuarioLogado);
                            break;
                        case "Meus Ingressos":
                            exibirMeusIngressos(usuarioLogado);
                            break;
                        case "Relatório de Vendas":
                            exibirRelatorioVendas();
                            break;
                    }
                }
            }
        });

        splitPane.setTopComponent(profilePanel);
        splitPane.setBottomComponent(menuPanel);

        JPanel mainContentPanel = new JPanel(new BorderLayout());
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        atualizarEventos(); // Chama o método atualizarEventos() aqui

        JScrollPane eventsScrollPane = new JScrollPane(eventsPanel);
        mainContentPanel.add(eventsScrollPane, BorderLayout.CENTER);

        JPanel sidePanel = new JPanel(new GridLayout(3, 1));

        infoText = new JTextArea();
        infoText.setEditable(false);
        infoText.setFont(new Font("Serif", Font.BOLD, 20));
        sidePanel.add(new JScrollPane(infoText));

        JPanel mapPanel = new ImagePanel("Cod/src/main/java/Assets/mapa.jpg");
        mapPanel.add(new JLabel("Localização"));
        sidePanel.add(mapPanel);

        JCalendar calendar = new JCalendar();
        sidePanel.add(calendar);

        add(splitPane, BorderLayout.WEST);
        add(mainContentPanel, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);

        setSize(1200, 1000);
        setVisible(true);
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

            JButton detailsButton = new JButton("Ver Detalhes");
            detailsButton.addActionListener(e -> {
                infoText.setText(
                        "Detalhes do Evento:\n"
                        + "Título: " + evento.getTitulo() + "\n"
                        + "Data e Hora: " + evento.getDataHora() + "\n"
                        + "Localização: " + evento.getLocalizacao() + "\n"
                        + "Descrição: " + evento.getDescricao() + "\n"
                        + "Capacidade: " + evento.getCapacidade() + "\n"
                        + "Valor Ingressos: " + evento.getValorIngressos() + "\n"
                        + "Organizador: " + organizador.getNome()
                );
            });
            buttonPanel.add(detailsButton);

            JButton buyTicketButton = new JButton("Comprar Ingresso");
            buyTicketButton.addActionListener(e -> {
                if (!usuarioLogado.isOrganizador()) {
                    FormularioCompraIngresso compraIngresso = new FormularioCompraIngresso(this, evento, usuarioLogado.getNome());
                    compraIngresso.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Apenas clientes podem comprar ingressos.", "Acesso Negado", JOptionPane.WARNING_MESSAGE);
                }
            });
            buttonPanel.add(buyTicketButton);

            eventPanel.add(buttonPanel, BorderLayout.SOUTH);

            eventsPanel.add(eventPanel);
        }
        eventsPanel.revalidate();
        eventsPanel.repaint();
    }

    private void realizarLogout() {
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente fazer logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                BancoDeDados.removerUsuarioLogado();
                dispose();
                BancoDeDados.main(new String[]{});
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void verificarEExibirFormularioEvento(int idOrganizador) throws SQLException {
        if (usuarioLogado.isOrganizador()) {
            FormularioEvento formularioEvento = new FormularioEvento(idOrganizador, this);
            formularioEvento.setLocationRelativeTo(null);
            formularioEvento.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Apenas organizadores podem cadastrar eventos.", "Acesso Negado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void exibirRelatorioVendas() {
        if (usuarioLogado.isOrganizador()) {
            RelatorioVendas relatorioVendas = new RelatorioVendas(this, usuarioLogado);
            relatorioVendas.setLocationRelativeTo(null);
            relatorioVendas.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Apenas organizadores podem acessar o relatório de vendas.", "Acesso Negado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void exibirMeusEventos(Usuario usuario, int userID) throws SQLException {
        if (usuarioLogado.isOrganizador()) {
            dispose();
            MeusEventos meusEventos = new MeusEventos(usuarioLogado, userID);
            meusEventos.setLocationRelativeTo(null);
            meusEventos.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Apenas organizadores podem acessar área de Eventos", "Acesso Negado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void exibirPerfil(Usuario usuario) {
        Perfil janelaPerfil = new Perfil(usuarioLogado);
        janelaPerfil.setVisible(true);
    }

    private void exibirMeusIngressos(Usuario usuario) {
        if (!usuarioLogado.isOrganizador()) {
            MeusIngressos janelaIngressos = new MeusIngressos(this, usuarioLogado);
            janelaIngressos.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Apenas clientes podem acessar área de Ingressos", "Acesso Negado", JOptionPane.WARNING_MESSAGE);
        }

    }

}
