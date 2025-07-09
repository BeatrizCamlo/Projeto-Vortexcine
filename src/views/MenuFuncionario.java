package views;

import repositories.*;
import entities.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MenuFuncionario {

    private final Scanner scanner;
    private final RepositorioFilmes repositorioFilmes;
    private final RepositorioFuncionarios repositorioFuncionarios;
    private final RepositorioClientes repositorioClientes;
    private final RepositorioSalas repositorioSalas;
    private final RepositorioSessao repositorioSessoes;
    private Funcionario funcionarioLogado;

    public MenuFuncionario(
        Scanner scanner,
        Funcionario funcionarioLogado,
        RepositorioFilmes repositorioFilmes,
        RepositorioFuncionarios repositorioFuncionarios,
        RepositorioClientes repositorioClientes,
        RepositorioSalas repositorioSalas,
        RepositorioSessao repositorioSessoes
    ) {
        this.scanner = scanner;
        this.funcionarioLogado = funcionarioLogado;
        this.repositorioFilmes = repositorioFilmes;
        this.repositorioFuncionarios = repositorioFuncionarios;
        this.repositorioClientes = repositorioClientes;
        this.repositorioSalas = repositorioSalas;
        this.repositorioSessoes = repositorioSessoes;
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
            System.out.println("7. Criar sala");
            System.out.println("8. Criar sessão");
            System.out.println("9. Exibir todas as Sessões");
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
                case 7: criarSala(); break;
                case 8: criarSessao(); break;
                case 9: visualizarSessoes(); break;
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
        var cliente = repositorioClientes.buscarporEmail(email);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.print("Novo nome (Enter para manter): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isBlank()) {
            repositorioClientes.modificarNomeCliente(cliente, novoNome);
        }

        System.out.print("Novo email (Enter para manter): ");
        String novoEmail = scanner.nextLine();
        if (!novoEmail.isBlank()) {
            repositorioClientes.modificarEmailCliente(cliente, novoEmail);
        }

        System.out.print("Nova senha (Enter para manter): ");
        String novaSenha = scanner.nextLine();
        if (!novaSenha.isBlank()) {
            repositorioClientes.modificarSenhaCliente(cliente, novaSenha);
        }

        System.out.println("Cliente atualizado com sucesso.");
    }

    private void removerCliente() {
        System.out.print("Digite o email do cliente a ser removido: ");
        String email = scanner.nextLine();
        var cliente = repositorioClientes.buscarporEmail(email);
        if (cliente != null) {
            repositorioClientes.removerCliente(cliente);
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

        if (repositorioFuncionarios.buscarporEmail(email) != null) {
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
        repositorioFuncionarios.adicionarFuncionario(novo);
        System.out.println("Funcionário cadastrado com sucesso.");
    }

    private void removerFuncionario() {
        System.out.print("Email do funcionário a remover: ");
        String email = scanner.nextLine();
        var funcionario = repositorioFuncionarios.buscarporEmail(email);
        if (funcionario != null) {
            repositorioFuncionarios.removerFuncionario(funcionario);
            System.out.println("Funcionário removido com sucesso.");
        } else {
            System.out.println("Funcionário não encontrado.");
        }
    }

    private void editarFuncionarios() {
        System.out.print("Email do funcionário a editar: ");
        String email = scanner.nextLine();
        var funcionario = repositorioFuncionarios.buscarporEmail(email);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        System.out.print("Novo nome (Enter para manter): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isBlank()) {
            repositorioFuncionarios.modificarNomeFuncionario(funcionario, novoNome);
        }

        System.out.print("Novo email (Enter para manter): ");
        String novoEmail = scanner.nextLine();
        if (!novoEmail.isBlank()) {
            repositorioFuncionarios.modificarEmailFuncionario(funcionario, novoEmail);
        }

        System.out.print("Nova senha (Enter para manter): ");
        String novaSenha = scanner.nextLine();
        if (!novaSenha.isBlank()) {
            repositorioFuncionarios.modificarSenhaFuncionario(funcionario, novaSenha);
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
        } catch (IllegalArgumentException e) {
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
            } catch (IllegalArgumentException e) {
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

    private void criarSala() {
        System.out.println("\n=== Criar Nova Sala ===");
        System.out.print("Digite o número da nova sala: ");
        int numero = Integer.parseInt(scanner.nextLine());

        Sala existente = repositorioSalas.buscarPorNumero(numero);
        if (existente != null) {
            System.out.println("Já existe uma sala com esse número.");
            return;
        }

        Sala novaSala = new Sala(numero);
        repositorioSalas.adicionarSala(novaSala);
        System.out.println("Sala " + numero + " criada com sucesso!");
    }

    private void criarSessao() {
        System.out.println("\n=== Criar Sessão ===");

        var filmes = repositorioFilmes.obterTodosFilmes();
        if (filmes.isEmpty()) {
            System.out.println("Nenhum filme cadastrado.");
            return;
        }

        System.out.println("Filmes disponíveis:");
        for (int i = 0; i < filmes.size(); i++) {
            System.out.println((i + 1) + ". " + filmes.get(i).getNome());
        }
        System.out.print("Escolha o filme: ");
        int opcaoFilme = Integer.parseInt(scanner.nextLine()) - 1;
        if (opcaoFilme < 0 || opcaoFilme >= filmes.size()) {
            System.out.println("Filme inválido.");
            return;
        }
        Filme filmeSelecionado = filmes.get(opcaoFilme);

        var salas = repositorioSalas.getTodasAsSalas();
        if (salas.isEmpty()) {
            System.out.println("Nenhuma sala cadastrada.");
            return;
        }

        System.out.println("Salas disponíveis:");
        for (int i = 0; i < salas.size(); i++) {
            System.out.println((i + 1) + ". Sala " + salas.get(i).getNumeroSala());
        }
        System.out.print("Escolha a sala: ");
        int opcaoSala = Integer.parseInt(scanner.nextLine()) - 1;
        if (opcaoSala < 0 || opcaoSala >= salas.size()) {
            System.out.println("Sala inválida.");
            return;
        }
        Sala salaSelecionada = salas.get(opcaoSala);

        try {
            System.out.print("Digite a data (ex: 2025-07-10): ");
            String data = scanner.nextLine();
            System.out.print("Digite a hora (ex: 14:30): ");
            String hora = scanner.nextLine();
            LocalDateTime dataHora = LocalDateTime.parse(data + "T" + hora);

            boolean conflito = salaSelecionada.getSessoes().stream()
                .anyMatch(s -> s.getDataHora().equals(dataHora));
            if (conflito) {
                System.out.println("Já existe uma sessão nesta sala neste horário.");
                return;
            }

            Sessao novaSessao = new Sessao(filmeSelecionado, salaSelecionada, dataHora);
            repositorioSessoes.adicionarSessao(novaSessao);
            salaSelecionada.adicionarSessao(novaSessao);
            repositorioSalas.ocuparSalaComSessao(salaSelecionada, novaSessao);

            System.out.println("Sessão criada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao interpretar data/hora. Tente novamente.");
        }
    }

    private void visualizarSessoes() {
        System.out.println("==== Sessões Cadastradas ====");

        List<Sessao> sessoes = repositorioSessoes.obterTodasSessoes();
        if (sessoes.isEmpty()) {
            System.out.println("Nenhuma sessão cadastrada.");
            return;
        }

        for (Sessao sessao : sessoes) {
            System.out.println("Filme: " + sessao.getFilme().getNome());
            System.out.println("Sala: " + sessao.getSala().getNumeroSala());
            System.out.println("Data/Hora: " + sessao.getDataHora());
            System.out.println("-------------------------");
        }
    }
}
