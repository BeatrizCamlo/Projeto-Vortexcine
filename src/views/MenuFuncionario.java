package views;

import services.*;
import entities.*;
import exceptions.*;
import java.util.Scanner;

public class MenuFuncionario {

    private final Scanner scanner;
    private final FilmeService filmeService;
    private final FuncionarioService funcionarioService;
    private final ClienteService clienteService;
    private final SalaService salaService;
    private final SessaoService sessaoService;
    private Funcionario funcionarioLogado;

    public MenuFuncionario(
        Scanner scanner,
        Funcionario funcionarioLogado,
        FilmeService filmeService,
        FuncionarioService funcionarioService,
        ClienteService clienteService,
        SalaService salaService,
        SessaoService sessaoService
        
    ) {
        this.scanner = scanner;
        this.funcionarioLogado = funcionarioLogado;
        this.filmeService = filmeService;
        this.funcionarioService = funcionarioService;
        this.clienteService = clienteService;
        this.salaService = salaService;
        this.sessaoService = sessaoService;
    }

    // talvez fazer um GerenciarFilme separado para reduzir esse menu do funcionário
    /*
     *      System.out.println("\n====== Menu Funcionário ======");
            System.out.println("1. Gerenciar Filmes");
            if (funcionarioLogado.ehGerente()) {
                System.out.println("2. Gerenciar Clientes");
                System.out.println("3. Gerenciar Funcionários");
            }
            System.out.println("4. Criar sala");
            System.out.println("5. Criar sessão");
            System.out.println("6. Exibir todas as Sessões");
            System.out.println("0. Deslogar");
            System.out.print("Opção: ");
     */
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
                case 3: mostrarFilmesEmCartaz(); break;
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


    // Parte do Cliente
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
        var clientes = clienteService.obterTodosClientes();
            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado.");
                return;
            }
        clientes.forEach(cliente -> cliente.exibirInformacoes());
    }

    private void editarCliente() {
        System.out.print("Digite o email do cliente a ser editado: ");
        String email = scanner.nextLine();

        Cliente cliente;
        try {
            cliente = clienteService.buscarPorEmail(email);
        } catch (CampoInvalido e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Novo nome (Enter para manter): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isBlank()) {
            try {
                clienteService.editarNome(cliente, novoNome);
            } catch (CampoInvalido e) {
                System.out.println("Erro ao editar nome: " + e.getMessage());
            }
        }

        System.out.print("Novo email (Enter para manter): ");
        String novoEmail = scanner.nextLine();
        if (!novoEmail.isBlank()) {
            try {
                clienteService.editarEmail(cliente, novoEmail);
            } catch (CampoInvalido e) {
                System.out.println("Erro ao editar email: " + e.getMessage());
            }
        }

        System.out.print("Nova senha (Enter para manter): ");
        String novaSenha = scanner.nextLine();
        if (!novaSenha.isBlank()) {
            try {
                clienteService.editarSenha(cliente, novaSenha);
            } catch (CampoInvalido e) {
                System.out.println("Erro ao editar senha: " + e.getMessage());
            }
        }

        System.out.println("Cliente atualizado com sucesso.");
    }

    private void removerCliente() {
        System.out.print("Digite o email do cliente a ser removido: ");
        String email = scanner.nextLine();

        try {
            Cliente cliente = clienteService.buscarPorEmail(email);
            clienteService.removerCliente(cliente);
            System.out.println("Cliente removido com sucesso.");
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // Parte do Funcionário
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
        var funcionarios = funcionarioService.obterTodosClientes();
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

        Funcionario novoFuncionario = new Funcionario(nome, email, senha, isGerente);
        try {
            funcionarioService.cadastrarFuncionario(novoFuncionario);
            System.out.println("Funcionário cadastrado com sucesso.");
        } catch (CampoInvalido e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    private void removerFuncionario() {
        System.out.print("Email do funcionário a remover: ");
        String email = scanner.nextLine();

        try {
            Funcionario funcionario = funcionarioService.buscarPorEmail(email);
            funcionarioService.removerFuncionario(funcionario);
            System.out.println("Funcionário removido com sucesso.");
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void editarFuncionarios() {
        System.out.print("Email do funcionário a editar: ");
        String email = scanner.nextLine();

        Funcionario funcionario;
        try {
            funcionario = funcionarioService.buscarPorEmail(email);
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        System.out.print("Novo nome (Enter para manter): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isBlank()) {
            try {
                funcionarioService.editarNome(funcionario, novoNome);
            } catch (CampoInvalido e) {
                System.out.println("Erro ao editar nome: " + e.getMessage());
            }
        }

        System.out.print("Novo email (Enter para manter): ");
        String novoEmail = scanner.nextLine();
        if (!novoEmail.isBlank()) {
            try {
                funcionarioService.editarEmail(funcionario, novoEmail);
            } catch (CampoInvalido e) {
                System.out.println("Erro ao editar email: " + e.getMessage());
            }
        }

        System.out.print("Nova senha (Enter para manter): ");
        String novaSenha = scanner.nextLine();
        if (!novaSenha.isBlank()) {
            try {
                funcionarioService.editarSenha(funcionario, novaSenha);
            } catch (CampoInvalido e) {
                System.out.println("Erro ao editar senha: " + e.getMessage());
            }
        }

        System.out.print("Novo status de gerente (s para gerente / n para comum / Enter para manter): ");
        String status = scanner.nextLine().trim().toLowerCase();
        if (status.equals("s")) {
            funcionarioService.promoverOuRebaixarGerente(funcionario, true);
            System.out.println("Funcionário promovido a gerente.");
        } else if (status.equals("n")) {
            funcionarioService.promoverOuRebaixarGerente(funcionario, false);
            System.out.println("Funcionário rebaixado de gerente.");
        }

        System.out.println("Funcionário atualizado com sucesso.");
    }

    //Parte dos Filmes
    private void cadastrarFilme() {
        System.out.println("====== Cadastrar Filme ======");
        System.out.print("Nome do Filme: ");
        String nome = scanner.nextLine();

        System.out.print("Duração (em minutos): ");
        int duracao;
        try {
            duracao = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Duração inválida.");
            return;
        }

        System.out.print("Gênero: ");
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
        System.out.println("====== Atualizar Filme ======");
        System.out.print("Digite o nome do filme: ");
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
        System.out.println("====== Deletar Filme ======");
        System.out.print("Digite o nome do filme: ");
        String nome = scanner.nextLine();

        try {
            Filme filme = filmeService.obterPorNome(nome);
            filmeService.removerFilme(funcionarioLogado, filme);
            System.out.println("Filme removido com sucesso.");
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void mostrarFilmesEmCartaz() {
        System.out.println(" ====== Filmes em Exibição ======");
        var filmes = filmeService.obterTodosFilmes();
        
        if (filmes.isEmpty()) {
            System.out.println("Nenhum filme encontrado.");
        } else {
            for (Filme filme : filmes) {
                String nome = filme.getNome();
                int duracao = filme.getDuracaoEmMinutos();
                String genero = filme.getGenero().toString();

                System.out.println("╔═══════════════════════════════════════╗");
                System.out.printf("║ Nome    : %-27s ║%n", nome);
                System.out.printf("║ Duração : %-27s ║%n", duracao + " min");
                System.out.printf("║ Gênero  : %-27s ║%n", genero);
                System.out.println("╚═══════════════════════════════════════╝");
                System.out.println();
            }
        }
    }

    // Parte da Sala

    private void criarSala() {
        System.out.println("====== Criar Sala ======");
        
        try {
            System.out.print("Número da sala: ");
            int numero = Integer.parseInt(scanner.nextLine());

            Sala novaSala = new Sala(numero);
            salaService.cadastrarSala(novaSala);
            System.out.println("Sala cadastrada com sucesso.");
            
        } catch (NumberFormatException e) {
            System.out.println("Erro: número inválido.");
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    // Parte das Sessões

    private void criarSessao() {
        System.out.println("====== Criar Sessão ======");

        try {
            // Buscar o filme
            System.out.print("Nome do filme: ");
            String nomeFilme = scanner.nextLine();
            Filme filme = filmeService.obterPorNome(nomeFilme);
            if (filme == null) {
                System.out.println("Filme não encontrado.");
                return;
            }

            System.out.print("Número da sala: ");
            int numeroSala = Integer.parseInt(scanner.nextLine());
            Sala sala = salaService.buscarSalaPorNumero(numeroSala);
            if (sala == null) {
                System.out.println("Sala não encontrada.");
                return;
            }

            System.out.print("Data e hora da sessão (formato livre): ");
            String dataHora = scanner.nextLine();
            if (dataHora.isBlank()) {
                System.out.println("Data e hora não podem estar vazias.");
                return;
            }

            Sessao novaSessao = new Sessao(filme, sala, dataHora);

            sessaoService.criarSessao(novaSessao);

            salaService.associarSessaoASala(sala, novaSessao);

            System.out.println("Sessão criada com sucesso.");

        } catch (NumberFormatException e) {
            System.out.println("Erro: número inválido.");
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void visualizarSessoes() {
        System.out.println("====== Sessões Cadastradas ======");
        try {
            sessaoService.exibirSessoes();
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
