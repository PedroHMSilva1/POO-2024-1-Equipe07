package Model.Beans;


public class Organizador extends Usuario {
    private boolean organizador;

    public Organizador() {
    }

    public Organizador(String nome, String senha, String email, String telefone, boolean organizador) {
        super(nome, senha, email, telefone, organizador);
        this.organizador = organizador;
    }

    public void setOrganizador(boolean organizador) {
        this.organizador = organizador;
    }

    @Override
    public boolean isOrganizador() {
        return organizador;
    }
}
