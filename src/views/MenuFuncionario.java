package views;

import repositories.*;
import entities.*;
import exceptions.*;
import java.util.Scanner;

public class MenuFuncionario {

    private final Scanner scanner;
    private final RepositorioFilmes repositorioFilmes;
    private final RepositorioFuncionarios repositorioFuncionarios;
    private final RepositorioClientes repositorioClientes;
    private Funcionario funcionarioLogado;

    public MenuFuncionario(
        Scanner scanner,
        Funcionario funcionarioLogado,
        RepositorioFilmes repositorioFilmes,
        RepositorioFuncionarios repositorioFuncionarios,
        RepositorioClientes repositorioClientes
    ) {
        this.scanner = scanner;
        this.funcionarioLogado = funcionarioLogado;
        this.repositorioFilmes = repositorioFilmes;
        this.repositorioFuncionarios = repositorioFuncionarios;
        this.repositorioClientes = repositorioClientes;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n====== Menu Funcionário ======");
            System.out.println("1. Cadastrar Filme");
            System.out.println("2. Atualizar Filme");
            System.out.println("3. Visualizar Filmes");
            System.out.println("4. Deletar Filme");

            if (funcionarioLogado.ehGerente()) {
                System.out.println("5. Gerenciar Clientes");
                System.out.println("6. Gerenciar Funcionários");
            }

            System.out.println("0. Deslogar");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1: cadastrarFilme(); break;
                case 2: atualizarFilme(); break;
                case 3: visualizarFilmes(); break;
                case 4: deletarFilmes(); break;
                case 5: {
                    if (funcionarioLogado.ehGerente()) {
                        gerenciarClientes();
                    }
                } break;
                case 6: {
                    if (funcionarioLogado.ehGerente()) {
                        gerenciarFuncionarios();
                    }
                } break;
                case 0: funcionarioLogado = null; break;
                default: System.out.println("Opção inválida."); break;
            }
        } while (funcionarioLogado != null);
    }


    private void gerenciarClientes() {
        int opcao;
        do {
            System.out.println("\n=== Gerenciar Clientes ===");
            System.out.println("1. Listar todos os clientes");
            System.out.println("2. Editar cliente");
            System.out.println("3. Remover cliente");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1: listarClientes(); break;
                case 2: editarCliente(); break;
                case 3: removerCliente(); break;
                case 0: System.out.println("Voltando..."); break;
                default: System.out.println("Opção inválida."); break;
            }
        } while (opcao != 0);
    }

    private void listarClientes() {
        var clientes = repositorioClientes.obterTodosClientes();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        clientes.forEach(cliente -> cliente.exibirInformacoes());
    }

    private void editarCliente() {
        System.out.print("Digite o email do cliente a ser editado: ");
        String email = scanner.nextLine();
        var cliente = repositorioClientes.obterPorEmail(email);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.print("Novo nome (Enter para manter): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isBlank()) {
            repositorioClientes.editarNome(cliente, novoNome);
        }

        System.out.print("Novo email (Enter para manter): ");
        String novoEmail = scanner.nextLine();
        if (!novoEmail.isBlank()) {
            repositorioClientes.editarEmail(cliente, novoEmail);
        }

        System.out.print("Nova senha (Enter para manter): ");
        String novaSenha = scanner.nextLine();
        if (!novaSenha.isBlank()) {
            repositorioClientes.editarSenha(cliente, novaSenha);
        }

        System.out.println("Cliente atualizado com sucesso.");
    }

    private void removerCliente() {
        System.out.print("Digite o email do cliente a ser removido: ");
        String email = scanner.nextLine();
        var cliente = repositorioClientes.obterPorEmail(email);
        if (cliente != null) {
            repositorioClientes.remover(cliente);
            System.out.println("Cliente removido com sucesso.");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }


    private void gerenciarFuncionarios() {
        int opcao;
        do {
            System.out.println("\n=== Gerenciar Funcionários ===");
            System.out.println("1. Listar todos");
            System.out.println("2. Editar");
            System.out.println("3. Adicionar");
            System.out.println("4. Remover");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1: listarFuncionarios(); break;
                case 2: editarFuncionarios(); break;
                case 3: cadastrarFuncionario(); break;
                case 4: removerFuncionario(); break;
                case 0: System.out.println("Voltando..."); break;
                default: System.out.println("Opção inválida."); break;
            }
        } while (opcao != 0);
    }

    private void listarFuncionarios() {
        var funcionarios = repositorioFuncionarios.obterTodosFuncionarios();
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }
        funcionarios.forEach(Funcionario::exibirInformacoes);
    }

    private void cadastrarFuncionario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (repositorioFuncionarios.obterPorEmail(email) != null) {
            System.out.println("Já existe um funcionário com esse email.");
            return;
        }

        boolean isGerente = false;
        while (true) {
            System.out.print("É gerente? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (resposta.equals("s")) {
                isGerente = true;
                break;
            } else if (resposta.equals("n")) {
                break;
            } else {
                System.out.println("Resposta inválida.");
            }
        }

        Funcionario novo = new Funcionario(nome, email, senha, isGerente);
        repositorioFuncionarios.cadastrar(novo);
        System.out.println("Funcionário cadastrado com sucesso.");
    }

    private void removerFuncionario() {
        System.out.print("Email do funcionário a remover: ");
        String email = scanner.nextLine();
        var funcionario = repositorioFuncionarios.buscarPorEmail(email);
        if (funcionario != null) {
            repositorioFuncionarios.remover(funcionario);
            System.out.println("Funcionário removido com sucesso.");
        } else {
            System.out.println("Funcionário não encontrado.");
        }
    }

    private void editarFuncionarios() {
        System.out.print("Email do funcionário a editar: ");
        String email = scanner.nextLine();
        var funcionario = repositorioFuncionarios.obterPorEmail(email);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        System.out.print("Novo nome (Enter para manter): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isBlank()) {
            repositorioFuncionarios.editarNome(funcionario, novoNome);
        }

        System.out.print("Novo email (Enter para manter): ");
        String novoEmail = scanner.nextLine();
        if (!novoEmail.isBlank()) {
            repositorioFuncionarios.editarEmail(funcionario, novoEmail);
        }

        System.out.print("Nova senha (Enter para manter): ");
        String novaSenha = scanner.nextLine();
        if (!novaSenha.isBlank()) {
            repositorioFuncionarios.editarSenha(funcionario, novaSenha);
        }

        System.out.print("Alterar status de gerente? (s/n): ");
        String resposta = scanner.nextLine().trim().toLowerCase();
        if (resposta.equals("s")) {
            repositorioFuncionarios.modificarStatusGerente(funcionario, !funcionario.ehGerente());
            System.out.println("Status de gerente alterado.");
        }

        System.out.println("Funcionário atualizado com sucesso.");
    }

    private void cadastrarFilme() {
        System.out.println("====== Cadastrar Filme ======");
        System.out.print("Nome do Filme: ");
        String nome = scanner.nextLine();

        System.out.print("Duração (em minutos): ");
        int duracao = Integer.parseInt(scanner.nextLine());

        System.out.print("Gênero: ");
        String generoStr = scanner.nextLine().toUpperCase();

        GeneroFilme genero = null;
        try {
            genero = GeneroFilme.valueOf(generoStr);
        } catch (CampoInvalido e) {
            System.out.println("Gênero inválido.");
            return;
        }

        Filme filme = new Filme(nome, duracao, genero);
        repositorioFilmes.adicionarFilme(funcionarioLogado, filme);

        System.out.println("Filme cadastrado com sucesso!");
    }

    private void atualizarFilme() {
        System.out.println("====== Atualizar Filme ======");
        System.out.print("Digite o nome do filme: ");
        String nome = scanner.nextLine();

        Filme filme = repositorioFilmes.obterFilmePorNome(nome);
        if (filme == null) {
            System.out.println("Filme não encontrado.");
            return;
        }

        System.out.print("Novo nome (Enter para manter): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isBlank()) {
            repositorioFilmes.modificarNomeFilme(funcionarioLogado, filme, novoNome);
        }

        System.out.print("Novo gênero (Enter para manter): ");
        String novoGenero = scanner.nextLine();
        if (!novoGenero.isBlank()) {
            try {
                GeneroFilme genero = GeneroFilme.valueOf(novoGenero.toUpperCase());
                repositorioFilmes.modificarGeneroFilme(funcionarioLogado, filme, genero);
            } catch (CampoInvalido e) {
                System.out.println("Gênero inválido. Gênero não alterado.");
            }
        }

        System.out.print("Nova duração (Enter para manter): ");
        String duracaoStr = scanner.nextLine();
        if (!duracaoStr.isBlank()) {
            try {
                int novaDuracao = Integer.parseInt(duracaoStr);
                repositorioFilmes.modificarDuracaoFilme(funcionarioLogado, filme, novaDuracao);
            } catch (NumberFormatException e) {
                System.out.println("Duração inválida. Duração não alterada.");
            }
        }

        System.out.println("Filme atualizado com sucesso.");
    }

    private void deletarFilmes() {
        System.out.println("====== Deletar Filme ======");
        System.out.print("Digite o nome do filme: ");
        String nome = scanner.nextLine();

        Filme filme = repositorioFilmes.obterFilmePorNome(nome);
        if (filme != null) {
            repositorioFilmes.removerFilme(funcionarioLogado, filme);
            System.out.println("Filme removido com sucesso.");
        } else {
            System.out.println("Filme não encontrado.");
        }
    }

    private void visualizarFilmes() {
        System.out.println("====== Filmes Cadastrados ======");
        var filmes = repositorioFilmes.obterTodosFilmes();
        if (filmes.isEmpty()) {
            System.out.println("Nenhum filme cadastrado.");
        } else {
            filmes.forEach(Filme::exibirInformacoes);
        }
    }
}
