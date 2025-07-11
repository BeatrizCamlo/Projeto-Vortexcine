package entities;

public class Ingresso {
    private final Sessao sessao;
    private final Assento assento;
    private final Cliente cliente;
    private final TipoIngresso tipo;
    private final double preco;

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

    public TipoIngresso getTipo() {
        return tipo;
    }

    public double getPreco() {
        return preco;
    }

    public void exibirInformacoes() {
        System.out.println("=== Ingresso ===");
        System.out.println("Filme: " + sessao.getFilme().getNome());
        System.out.println("Data/Hora: " + sessao.getDataHora());
        System.out.println("Sala: " + sessao.getSala().getNumeroSala());
        System.out.println("Assento: " + assento.getCoordenada());
        System.out.println("Tipo: " + tipo);
        System.out.printf("Preço: R$ %.2f\n", preco);
        System.out.println("Cliente: " + cliente.getNome());
    }
}
