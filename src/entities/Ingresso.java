package entities;

public class Ingresso {
    private Sessao sessao;
    private Assento assento;
    private Cliente cliente;
    private TipoIngresso tipo;
    private double preco;

    public Ingresso(Sessao sessao, Assento assento, Cliente cliente, TipoIngresso tipo, double preco) {
        this.sessao = sessao;
        this.assento = assento;
        this.cliente = cliente;
        this.tipo = tipo;
        this.preco = preco;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public Assento getAssento() {
        return assento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getPreco() {
        return preco;
    }

    public TipoIngresso getTipo() {
        return tipo;
    }

    public void exibirInformacoes() {
        System.out.println("=== Ingresso ===");
        System.out.println("Filme: " + sessao.getFilme().getNome());
        System.out.println("Data/Hora: " + sessao.getDataHora());
        System.out.println("Sala: " + sessao.getSala().getNumeroSala());
        System.out.println("Assento: " + assento.getCoordenada());
        System.out.println("Tipo: " + tipo);
        System.out.println("Preço: R$ " + preco);
        System.out.println("Cliente: " + cliente.getNome());
    }
}
