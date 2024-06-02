package Model.DAO;

import Model.Beans.Evento;
import Model.Beans.Organizador;
import Model.Beans.Usuario;
import Model.Beans.VendaEvento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import view.Formulario;
import view.Login;

public class BancoDeDados {

    static ConexaoMySQL conexao = new ConexaoMySQL();
    static Connection cn = null;
    static PreparedStatement ps = null;

    public static void main(String[] args) {

        ImageIcon eventIcon = new ImageIcon("Cod/src/main/java/Assets/EventMaster.jpg");
        try {
            String operacao[] = {"Cadastro de Usuário", "Login"};
            Object escolha = JOptionPane.showInputDialog(null, "O que deseja realizar?", "EventMaster", JOptionPane.QUESTION_MESSAGE, eventIcon, operacao, operacao[0]);
            realizaOperacao(escolha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void realizaOperacao(Object escolha) throws Exception {
        if (escolha.toString().equals("Cadastro de Usuário")) {
            Formulario formulario = new Formulario();
            formulario.setLocationRelativeTo(null);
            formulario.setVisible(true);
        } else if (escolha.toString().equals("Login")) {
            Login login = new Login();
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        }
    }

    public static void cadastrarUsuario(Usuario usuario) throws SQLException {
        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement(
                    "INSERT INTO senac.usuarios (Nome, Email, Senha, Telefone, Organizador) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getTelefone());
            ps.setBoolean(5, usuario.isOrganizador());
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public static int verificarLogin(String email, String senha) throws SQLException {
        ResultSet rs = null;
        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement(
                    "SELECT id FROM senac.usuarios WHERE Email = ? AND Senha = ?");
            ps.setString(1, email);
            ps.setString(2, senha);
            rs = ps.executeQuery();
            if (rs.next()) {
                int userID = rs.getInt("id");
                // Após o login bem-sucedido, inserir o usuário na tabela Login
                inserirUsuarioLogado(userID);
                return userID; // Retorna o ID do usuário se o login for bem-sucedido
            } else {
                return -1; // Retorna -1 se o login falhar
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public static void inserirUsuarioLogado(int userID) throws SQLException {
        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement("INSERT INTO Login (UserID) VALUES (?)");
            ps.setInt(1, userID);
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public static boolean verificarUsuarioLogado(int userID) throws SQLException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = cn.prepareStatement("SELECT * FROM Login WHERE UserID = ?");
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public static void removerUsuarioLogado() throws SQLException {
        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement("DELETE FROM Login");
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public static Usuario recuperarUsuario(int userID) throws SQLException {
        Usuario usuario = null;
        ResultSet rs = null;
        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement("SELECT * FROM senac.usuarios WHERE id = ?");
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                // Extrair informações do ResultSet e criar um objeto Usuario
                String nome = rs.getString("Nome");
                String email = rs.getString("Email");
                String senha = rs.getString("Senha");
                String telefone = rs.getString("Telefone");
                boolean organizador = rs.getBoolean("Organizador");

                // Criar o objeto Usuario
                usuario = new Organizador(nome, senha, email, telefone, organizador);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return usuario;
    }

    public static void cadastrarEvento(Evento evento) throws SQLException {
        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement(
                    "INSERT INTO senac.eventos (titulo, data_hora, localizacao, descricao, capacidade, valor_ingressos, id_organizador) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, evento.getTitulo());
            ps.setString(2, evento.getDataHora());
            ps.setString(3, evento.getLocalizacao());
            ps.setString(4, evento.getDescricao());
            ps.setInt(5, evento.getCapacidade());
            ps.setDouble(6, evento.getValorIngressos());
            ps.setInt(7, evento.getIdOrganizador());
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public static ArrayList<Evento> recuperarEventos() throws SQLException {
        ArrayList<Evento> eventos = new ArrayList<>();

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement("SELECT * FROM senac.eventos");
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String dataHora = rs.getString("data_hora");
                String localizacao = rs.getString("localizacao");
                String descricao = rs.getString("descricao");
                int capacidade = rs.getInt("capacidade");
                double valorIngressos = rs.getDouble("valor_ingressos");
                int idOrganizador = rs.getInt("id_organizador");

                // Criar um objeto Evento e adicioná-lo à lista de eventos
                Evento evento = new Evento(id, titulo, dataHora, localizacao, descricao, capacidade, valorIngressos, idOrganizador);
                eventos.add(evento);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }

        return eventos;
    }

    public static ArrayList<Evento> recuperarMeusEventos(int userId) throws SQLException {
        ArrayList<Evento> eventos = new ArrayList<>();

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement("SELECT * FROM senac.eventos WHERE id_organizador = ?");
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String dataHora = rs.getString("data_hora");
                String localizacao = rs.getString("localizacao");
                String descricao = rs.getString("descricao");
                int capacidade = rs.getInt("capacidade");
                double valorIngressos = rs.getDouble("valor_ingressos");
                int idOrganizador = rs.getInt("id_organizador");

                // Criar um objeto Evento e adicioná-lo à lista de eventos
                Evento evento = new Evento(id, titulo, dataHora, localizacao, descricao, capacidade, valorIngressos, idOrganizador);
                eventos.add(evento);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }

        return eventos;
    }

    public static Map<Integer, VendaEvento> recuperarVendasPorEvento() throws SQLException {
        Map<Integer, VendaEvento> vendasPorEvento = new HashMap<>();

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement("SELECT e.id AS evento_id, e.titulo AS nome_evento, c.quantidade, c.valor_pago FROM compras c INNER JOIN eventos e ON c.evento_id = e.id");
            rs = ps.executeQuery();

            while (rs.next()) {
                int eventoId = rs.getInt("evento_id");
                String nomeEvento = rs.getString("nome_evento");
                int quantidade = rs.getInt("quantidade");
                double valorPago = rs.getDouble("valor_pago");

                if (vendasPorEvento.containsKey(eventoId)) {
                    VendaEvento vendaEvento = vendasPorEvento.get(eventoId);
                    vendaEvento.adicionarVenda(quantidade, valorPago);
                } else {
                    VendaEvento vendaEvento = new VendaEvento(nomeEvento, quantidade, valorPago);
                    vendasPorEvento.put(eventoId, vendaEvento);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }

        return vendasPorEvento;
    }
    
    public static void alteraEvento(Evento evento) throws SQLException {
    Connection cn = null;
    PreparedStatement ps = null;

    try {
        cn = conexao.openDB();
        ps = cn.prepareStatement(
            "UPDATE senac.eventos SET titulo = ?, localizacao = ?, descricao = ? WHERE id = ?"
        );
        ps.setString(1, evento.getTitulo());
        ps.setString(2, evento.getLocalizacao());
        ps.setString(3, evento.getDescricao());
        ps.setInt(4, evento.getId());
        
        ps.executeUpdate();
    } finally {
        if (ps != null) {
            ps.close();
        }
        if (cn != null) {
            cn.close();
        }
    }
}


    public static Evento recuperarEventoPorId(int id) throws SQLException {
        ArrayList<Evento> eventos = recuperarEventos();
        for (Evento evento : eventos) {
            if (evento.getId() == id) {
                return evento;
            }
        }
        return null; // Retorna null se o evento com o ID especificado não for encontrado
    }

    public static void excluirEvento(int eventoId) throws SQLException {
        Connection cn = null;
        PreparedStatement psIngressos = null;
        PreparedStatement psEvento = null;

        try {
            cn = conexao.openDB();
            cn.setAutoCommit(false);  // Inicia a transação

            // Primeiro exclua os ingressos relacionados ao evento
            psIngressos = cn.prepareStatement("DELETE FROM senac.compras WHERE evento_id = ?");
            psIngressos.setInt(1, eventoId);
            psIngressos.executeUpdate();

            // Em seguida, exclua o evento
            psEvento = cn.prepareStatement("DELETE FROM senac.eventos WHERE id = ?");
            psEvento.setInt(1, eventoId);
            psEvento.executeUpdate();

            cn.commit();  // Confirma a transação
        } catch (SQLException e) {
            if (cn != null) {
                cn.rollback();  // Desfaz a transação em caso de erro
            }
            throw e;
        } finally {
            if (psIngressos != null) {
                psIngressos.close();
            }
            if (psEvento != null) {
                psEvento.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public static int recuperarIngressosPorEvento(int eventoId) throws SQLException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement("SELECT COUNT(*) AS total FROM senac.compras WHERE evento_id = ?");
            ps.setInt(1, eventoId);
            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt("total");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }

        return count;
    }

}
