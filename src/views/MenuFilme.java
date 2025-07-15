package views;

import java.util.Scanner;

import abstracts.MenuBase;
import entities.Cliente;
import entities.Filme;
import entities.Funcionario;
import entities.GeneroFilme;
import exceptions.CampoInvalido;
import repositories.RepositorioFilmes;
import services.FilmeService;

public class MenuFilme extends MenuBase {

    private final FilmeService filmeService;
    private final Funcionario funcionarioLogado;
    private final Scanner scanner;

    public MenuFilme(Funcionario funcionarioLogado, Scanner scanner) {
        this.filmeService = new FilmeService(new RepositorioFilmes());
        this.funcionarioLogado = funcionarioLogado;
        this.scanner = scanner;
    }

    public MenuFilme(Cliente clienteLogado, Scanner scanner) {
        this.funcionarioLogado = null;
        this.filmeService = new FilmeService(new RepositorioFilmes());
        this.scanner = scanner;
    }

    @Override
    public void exibirMenu() {
        int opcao;
        do {
            exibirCabecalho("Menu Filme");
            System.out.println("1. Cadastrar Filme");
            System.out.println("2. Editar Filme");
            System.out.println("3. Remover Filme");
            System.out.println("4. Listar Filmes");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao(scanner);
            tratarOpcao(opcao);
        } while (opcao != 0);
    }

    @Override
    protected void tratarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> cadastrarFilme();
            case 2 -> atualizarFilme();
            case 3 -> deletarFilmes();
            case 4 -> mostrarFilmesEmCartaz();
            case 0 -> System.out.println("Saindo do menu de filmes.");
            default -> System.out.println("Opção inválida, tente novamente.");
        }
    }


    private void cadastrarFilme() {
        exibirCabecalho("Cadastrar Filme");

        System.out.print("Nome do Filme: ");
        String nome = scanner.nextLine();

        int duracao;
        try {
            System.out.print("Duração (em minutos): ");
            duracao = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Duração inválida.");
            return;
        }

        System.out.print("Gênero (ex: ACAO, COMEDIA, DRAMA...): ");
        String generoStr = scanner.nextLine().toUpperCase();

        GeneroFilme genero;
        try {
            genero = GeneroFilme.valueOf(generoStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Gênero inválido.");
            return;
        }

        Filme filme = new Filme(nome, duracao, genero);
        try {
            filmeService.adicionarFilme(funcionarioLogado, filme);
            System.out.println("Filme cadastrado com sucesso!");
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void atualizarFilme() {
        exibirCabecalho("Atualizar Filme");

        System.out.print("Nome do filme a editar: ");
        String nome = scanner.nextLine();

        Filme filme;
        try {
            filme = filmeService.obterPorNome(nome);
            if (filme == null) {
                System.out.println("Filme não encontrado.");
                return;
            }
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        System.out.print("Novo nome (Enter para manter): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isBlank()) {
            try {
                filmeService.modificarNome(funcionarioLogado, filme, novoNome);
            } catch (CampoInvalido e) {
                System.out.println("Erro ao alterar nome: " + e.getMessage());
            }
        }

        System.out.print("Novo gênero (Enter para manter): ");
        String novoGenero = scanner.nextLine();
        if (!novoGenero.isBlank()) {
            try {
                GeneroFilme genero = GeneroFilme.valueOf(novoGenero.toUpperCase());
                filmeService.modificarGenero(funcionarioLogado, filme, genero);
            } catch (IllegalArgumentException e) {
                System.out.println("Gênero inválido. Gênero não alterado.");
            } catch (CampoInvalido e) {
                System.out.println("Erro ao alterar gênero: " + e.getMessage());
            }
        }

        System.out.print("Nova duração (Enter para manter): ");
        String duracaoStr = scanner.nextLine();
        if (!duracaoStr.isBlank()) {
            try {
                int novaDuracao = Integer.parseInt(duracaoStr);
                filmeService.modificarDuracao(funcionarioLogado, filme, novaDuracao);
            } catch (NumberFormatException e) {
                System.out.println("Duração inválida. Duração não alterada.");
            } catch (CampoInvalido e) {
                System.out.println("Erro ao alterar duração: " + e.getMessage());
            }
        }

        System.out.println("Filme atualizado com sucesso.");
    }

    private void deletarFilmes() {
        exibirCabecalho("Remover Filme");

        System.out.print("Digite o nome do filme: ");
        String nome = scanner.nextLine();

        try {
            Filme filme = filmeService.obterPorNome(nome);
            if (filme == null) {
                System.out.println("Filme não encontrado.");
                return;
            }

            filmeService.removerFilme(funcionarioLogado, filme);
            System.out.println("Filme removido com sucesso.");
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void mostrarFilmesEmCartaz() {
        exibirCabecalho("Filmes em Exibição");

        var filmes = filmeService.obterTodosFilmes();
        if (filmes.isEmpty()) {
            System.out.println("Nenhum filme encontrado.");
        } else {
            for (Filme filme : filmes) {
                System.out.println("╔═══════════════════════════════════════╗");
                System.out.printf("║ Nome    : %-27s ║%n", filme.getNome());
                System.out.printf("║ Duração : %-27s ║%n", filme.getDuracaoEmMinutos() + " min");
                System.out.printf("║ Gênero  : %-27s ║%n", filme.getGenero());
                System.out.println("╚═══════════════════════════════════════╝\n");
            }
        }
    }
}
