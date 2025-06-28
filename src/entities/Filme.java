package entities;

public class Filme {

    private String nome;
    private int duracaoEmMinutos;
    private GeneroFilme genero;

    public Filme(String nome, int duracaoEmMinutos, GeneroFilme genero) {
        this.nome = nome;
        this.duracaoEmMinutos = duracaoEmMinutos;
        this.genero = genero;
    }

    public String getNome() {
        return nome;
    }

    public int getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }

    public GeneroFilme getGenero() {
        return genero;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDuracaoEmMinutos(int duracaoEmMinutos) {
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

    public void setGenero(GeneroFilme genero) {
        this.genero = genero;
    }

    public void exibirInformacoes() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.printf ("║ Nome    : %-27s ║%n", nome);
        System.out.printf ("║ Duração : %-27s ║%n", duracaoEmMinutos + " min");
        System.out.printf ("║ Gênero  : %-27s ║%n", genero);
        System.out.println("╚═══════════════════════════════════════╝");
    }
}
