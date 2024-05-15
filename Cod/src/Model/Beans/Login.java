package Model.Beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Login extends Usuario {
    private Connection connection;

    public Login(Connection connection) {
        this.connection = connection;
    }

    public Login() {
    }

    public void salvar(Login login) throws SQLException {
        String sql = "INSERT INTO login (nome, email, senha, telefone, endereco, Data) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login.getNome());
            statement.setString(2, login.getEmail());
            statement.setString(3, login.getSenha());
            statement.setString(4, login.getTelefone());
            statement.setString(5, login.getEndereco());
            statement.setDate(6, login.getData());
            statement.executeUpdate();
        }
    }

    public List<Login> listarTodos() throws SQLException {
        List<Login> logins = new ArrayList<>();
        String sql = "SELECT * FROM login";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Login login = new Login();
                    login.setNome(resultSet.getString("nome"));
                    login.setEmail(resultSet.getString("email"));
                    login.setSenha(resultSet.getString("senha"));
                    login.setTelefone(resultSet.getString("telefone"));
                    login.setEndereco(resultSet.getString("endereco"));
                    login.setData(resultSet.getDate("data"));
                    
                    logins.add(login);
                }
            }
        }
        return logins;
    }
}
