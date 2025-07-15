package data;

import services.*;
import entities.*;

import java.time.LocalDateTime;

public class DataSeeder {
    private final ClienteService clienteService;
    private final FilmeService filmeService;
    private final FuncionarioService funcionarioService;
    private final SalaService salaService;
    private final SessaoService sessaoService;

    public DataSeeder(
        ClienteService clienteService,
        FilmeService filmeService,
        FuncionarioService funcionarioService,
        SalaService salaService,
        SessaoService sessaoService
    ) {
        this.clienteService = clienteService;
        this.filmeService = filmeService;
        this.funcionarioService = funcionarioService;
        this.salaService = salaService;
        this.sessaoService = sessaoService;
    }

    public void carregarDados() {
        inicializarClientesPadrao();
        inicializarFilmes();
        inicializarFuncionarios();
        inicializarSalas();
        inicializarSessoes();
    }

    private void inicializarClientesPadrao() {
        try {
            clienteService.cadastrarCliente(new Cliente("João", "jaofilho@gmail.com", "joao"));
            clienteService.cadastrarCliente(new Cliente("Gustavo", "gustag@hotmail.com", "gustavo"));
            clienteService.cadastrarCliente(new Cliente("Bianca", "biacac@yahoo.com", "bianca"));
            clienteService.cadastrarCliente(new Cliente("Vilma", "vilminha@outlook.com", "vilma"));
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar clientes: " + e.getMessage());
        }
    }

    private void inicializarFilmes() {

        filmeService.adicionarFilmeaoBanco(new Filme("Matrix", 136, GeneroFilme.ACAO));
        filmeService.adicionarFilmeaoBanco(new Filme("O Poderoso Chefão", 175, GeneroFilme.DRAMA));
        filmeService.adicionarFilmeaoBanco(new Filme("Toy Story", 81, GeneroFilme.ANIMACAO));
        filmeService.adicionarFilmeaoBanco(new Filme("Titanic", 195, GeneroFilme.ROMANCE));
        filmeService.adicionarFilmeaoBanco(new Filme("Vingadores: Ultimato", 181, GeneroFilme.ACAO));
    }

    private void inicializarFuncionarios() {
        try {
            funcionarioService.cadastrarFuncionario(new Funcionario("adm", "adm@vortexcine.com", "adm", true));
            funcionarioService.cadastrarFuncionario(new Funcionario("moises", "moises@vortexcine.com", "moises", false));
            funcionarioService.cadastrarFuncionario(new Funcionario("ana", "anabea@vortexcine.com", "ana", false));
            funcionarioService.cadastrarFuncionario(new Funcionario("rodrigo", "rodrigo@vortexcine.com", "rodrigo", false));
            funcionarioService.cadastrarFuncionario(new Funcionario("maria", "marilu@vortexcine.com", "maria", false));
            funcionarioService.cadastrarFuncionario(new Funcionario("joao", "joca@vortexcine.com", "joao", false));
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar funcionários: " + e.getMessage());
        }
    }

    private void inicializarSalas() {
        try {
            salaService.cadastrarSala(new Sala(1));
            salaService.cadastrarSala(new Sala(2));
            salaService.cadastrarSala(new Sala(3));
        } catch (Exception e) {
            System.out.println("Erro ao criar salas: " + e.getMessage());
        }
    }

    private void inicializarSessoes() {
        try {
            Filme filme = filmeService.obterPorNome("Matrix");
            Sala sala = salaService.buscarSalaPorNumero(1);
            Sessao sessao = new Sessao(filme, sala, LocalDateTime.of(2025, 7, 17, 16, 30));
            sessaoService.criarSessao(sessao);
            salaService.associarSessaoASala(sala, sessao);

            Filme filme2 = filmeService.obterPorNome("Titanic");
            Sala sala2 = salaService.buscarSalaPorNumero(2);
            Sessao sessao2 = new Sessao(filme2, sala2, LocalDateTime.of(2025, 7, 17,17, 50));
            sessaoService.criarSessao(sessao2);
            salaService.associarSessaoASala(sala2, sessao2);
        } catch (Exception e) {
            System.out.println("Erro ao criar sessões: " + e.getMessage());
        }
    }
}
