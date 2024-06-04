package Model.Dao;
import Model.Beans.*;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PagamentoPix implements FormaPagamento {
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private ImageIcon imagemPagamento;

    public PagamentoPix() {
        // Carregar a imagem de pagamento fixa
        imagemPagamento = new ImageIcon("Cod/src/main/java/Assets/QRCode.PNG");
    }

    @Override
    public void realizarPagamento(double valor) throws PagamentoException {
        String faturaPix = gerarFaturaPix();
        JOptionPane.showMessageDialog(null, "Pagamento via Pix de R$" + valor + "\nFatura PIX: " + faturaPix, "Pagamento via Pix", JOptionPane.INFORMATION_MESSAGE, imagemPagamento);
    }

    // Gerar um valor aleatório para a fatura PIX entre 50 e 150 caracteres
    private String gerarFaturaPix() {
        Random random = new Random();
        int tamanho = random.nextInt(101) + 50;
        StringBuilder sb = new StringBuilder(tamanho);
        for (int i = 0; i < tamanho; i++) {
            int indice = random.nextInt(CARACTERES.length());
            sb.append(CARACTERES.charAt(indice));
        }
        return sb.toString();
    }
}
