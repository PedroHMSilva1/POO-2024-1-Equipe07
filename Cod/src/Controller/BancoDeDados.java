package Controller;

import Model.Beans.*;

import java.sql.*;
import javax.swing.JOptionPane;
import view.Cadastro;

public class BancoDeDados {
    static ConexaoMySQL conexao = new ConexaoMySQL();
    static Connection cn = null;
    static PreparedStatement ps = null;

    public static void main(String[] args) {
        try {
            String operacao[] = {"Cadastro", "Login"};
            Object escolha = JOptionPane.showInputDialog(null, "O que deseja realizar?", "", JOptionPane.QUESTION_MESSAGE, null, operacao, operacao[0]);
            realizaOperacao(escolha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void realizaOperacao(Object escolha) throws Exception {
        try {
            cn = conexao.openDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (escolha.toString().equals("Cadastro"))
            cadastrarUsuario();
        else if (escolha.toString().equals("Login"))
            new Login();
    }

    private static void cadastrarUsuario() throws SQLException {
        Cadastro usuario = new Cadastro();
        
        ps = cn.prepareStatement("INSERT INTO Usuarios (Nome, Email, Senha, Telefone, Data, Organizador) " + "VALUES (?, ?, ?, ?, ?, ?)");
        ps.setString(1, usuario.getNomeFieldText());
        ps.setString(2, usuario.getEmailFieldText());
        ps.setString(3, usuario.getSenhaFieldText());
        ps.setString(4, usuario.getTelefoneFieldText());
        ps.setString(5, usuario.getDataNascimentoFieldText());
        ps.setBoolean(6, usuario.isOrganizadorSelected());
        
        ps.executeUpdate();
        cn.close();
        ps.close();
    }
}
