package Model.DAO;

import Controller.ConexaoMySQL;
import Model.Beans.Compra;
import Model.Beans.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class IngressoUsuarioDAO {
    private ConexaoMySQL conexao = new ConexaoMySQL();

    public ArrayList<Compra> recuperarComprasPorUsuario(Usuario usuarioLogado) throws SQLException {
    ArrayList<Compra> compras = new ArrayList<>();
    Connection cn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
        cn = conexao.openDB();
        ps = cn.prepareStatement("SELECT * FROM senac.compras WHERE nome_comprador = ?");
        ps.setString(1, usuarioLogado.getNome()); // Usar o nome do comprador como parâmetro
        rs = ps.executeQuery();
        while (rs.next()) {
            int eventoId = rs.getInt("evento_id");
            boolean meiaEntrada = rs.getBoolean("meia_entrada");
            int quantidade = rs.getInt("quantidade");
            double valorIngresso = rs.getDouble("valor_ingresso");
            double valorPago = rs.getDouble("valor_pago");
            String formaPagamento = rs.getString("forma_pagamento");
            String nomeComprador = rs.getString("nome_comprador");
            LocalDateTime dataHoraCompra = rs.getObject("data_hora_compra", LocalDateTime.class);

            Compra compra = new Compra(eventoId, meiaEntrada, quantidade, valorIngresso, valorPago, formaPagamento, nomeComprador, dataHoraCompra);
            compras.add(compra);
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
    return compras;
}

}
