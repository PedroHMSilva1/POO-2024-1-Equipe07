package Controller;

import java.sql.*;

public class ConexaoMySQL {
    private final static String url = "jdbc:mysql://localhost:3306/Senac";
    private final static String username = "root";
    private final static String password = "guilhermehvs";

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

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
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.out.println("\nNão foi possível fechar conexão " + e + "\n");
            System.exit(1);
        }
    }
}
