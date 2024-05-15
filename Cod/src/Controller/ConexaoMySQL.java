package Controller;

import java.sql.*;

import Model.Beans.*;

public class ConexaoMySQL {
    private final static String url = "jdbc:mysql://localhost:3306/PI";
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
                "INSERT INTO Usuarios (nome, email, senha, telefone, data, endereco)" +
                " VALUES (?, ?, ?, ?, ?, ?)");
    
            Usuario usuario = new Usuario();
            usuario.setNome("Exemplo");
            usuario.setEmail("exemplo@email.com");
            usuario.setSenha("senha123");
            usuario.setTelefone("123456789");

            usuario.setEndereco("Rua Exemplo, 123");
    
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getTelefone());

            ps.setDate(5, new java.sql.Date(System.currentTimeMillis())); // example for current date
            ps.setString(6, usuario.getEndereco());
    
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
