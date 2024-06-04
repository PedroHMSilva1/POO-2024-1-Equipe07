package Model.Dao;
import Model.Beans.*;

// PagamentoBoleto.java
import javax.swing.JOptionPane;

public class PagamentoBoleto implements FormaPagamento {
    @Override
    public void realizarPagamento(double valor) throws PagamentoException {
        // Lógica para pagamento via Boleto
        JOptionPane.showMessageDialog(null,"Boleto enviado para seu email");
    }
}
