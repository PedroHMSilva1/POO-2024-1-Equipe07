package Controller;

import java.sql.*;
import view.Cadastro;

public class ConexaoMySQL {
    private final static String url = "jdbc:mysql://localhost:3306/Senac";
    private final static String username = "root";
    private final static String password = "";

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public static void main(String[] args) {
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection cn = conexao.openDB();

            PreparedStatement ps = cn.prepareStatement(
                "INSERT INTO Usuarios (Nome, Email, Senha, Telefone, Data, Organizador)" +
                " VALUES (?, ?, ?, ?, ?, ?)");

            Cadastro usuario = new Cadastro();
            usuario.setNomeFieldText("Exemplo");
            usuario.setEmailFieldText("exemplo@email.com");
            usuario.setSenhaFieldText("senha123");
            usuario.setTelefoneFieldText("+55(11)95555-6666");
            usuario.setDataNascimentoFieldText("01/01/2000");
            usuario.setOrganizadorSelected(true);

            ps.setString(1, usuario.getNomeFieldText());
            ps.setString(2, usuario.getEmailFieldText());
            ps.setString(3, usuario.getSenhaFieldText());
            ps.setString(4, usuario.getTelefoneFieldText());
            ps.setString(5, usuario.getDataNascimentoFieldText());
            ps.setBoolean(6, usuario.isOrganizadorSelected());

            ps.executeUpdate();
            System.out.println("Usuário cadastrado com sucesso.");

            conexao.closeDB();
        } catch (SQLException e) {
            System.out.println("Falha ao realizar a operação.");
            e.printStackTrace();
        }
    }

    public Connection openDB() {
        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("\nConexão estabelecida com sucesso!\n");
        } catch (SQLException e) {
            System.out.println("\nNão foi possível estabelecer conexão " + e + "\n");
            System.exit(1);
        }
        return con;
    }

    public void closeDB() {
        try {
            if (ps != null) ps.close();
            if (rs != null) rs.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("\nNão foi possível fechar conexão " + e + "\n");
            System.exit(1);
        }
    }
}
