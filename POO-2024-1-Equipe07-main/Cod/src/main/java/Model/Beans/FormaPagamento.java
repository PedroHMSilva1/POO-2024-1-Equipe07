package Model.Beans;

public interface FormaPagamento {
    void realizarPagamento(double valor) throws PagamentoException;
}
