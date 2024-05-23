package Model.DAO;

import Controller.ConexaoMySQL;
import Model.Beans.Compra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompraDAO {
    private ConexaoMySQL conexao = new ConexaoMySQL();

    public void registrarCompra(Compra compra) throws SQLException {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement(
                    "INSERT INTO senac.compras (evento_id, meia_entrada, quantidade, valor_ingresso, valor_pago, forma_pagamento, nome_comprador, data_hora_compra) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, compra.getEventoId());
            ps.setBoolean(2, compra.isMeiaEntrada());
            ps.setInt(3, compra.getQuantidade());
            ps.setDouble(4, compra.getValorIngresso());
            ps.setDouble(5, compra.getValorPago());
            ps.setString(6, compra.getFormaPagamento());
            ps.setString(7, compra.getNomeComprador());
            ps.setObject(8, compra.getDataHoraCompra());
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
}
