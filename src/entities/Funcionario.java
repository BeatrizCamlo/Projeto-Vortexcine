package entities;

import java.time.LocalDateTime;
import java.util.List;

public class Funcionario extends Pessoa {
    private boolean ehGerente;

    public Funcionario(String nome, String email, String senha, boolean ehGerente) {
        super(nome, email, senha);
        this.ehGerente = ehGerente;
    }

    public boolean ehGerente() {
        return ehGerente;
    }

    public void setEhGerente(boolean ehGerente) {
        this.ehGerente = ehGerente;
    }

    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Cargo: " + (ehGerente ? "Gerente" : "Funcionário"));
    }

    public Filme cadastrarFilme(String nome, int duracao, GeneroFilme genero, List<Filme> listaFilmes) {
        Filme filme = new Filme(nome, duracao, genero);
        listaFilmes.add(filme);
        System.out.println("Filme cadastrado: " + nome);
        return filme;
    }

    public Sessao criarSessao(Filme filme, Sala sala, LocalDateTime dataHora, List<Sessao> listaSessoes) {
        Sessao sessao = new Sessao(filme, sala, dataHora);
        listaSessoes.add(sessao);
        System.out.println("Sessão criada para o filme " + filme.getNome() + " na sala " + sala.getNumeroSala() + " em " + dataHora);
        return sessao;
    }

}
