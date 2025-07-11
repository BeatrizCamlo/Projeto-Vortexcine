package views;

import abstracts.MenuBase;
import entities.*;
import exceptions.CampoInvalido;
import services.*;

import java.util.Scanner;

public class MenuFuncionario extends MenuBase {
    private final Scanner scanner;
    private Funcionario funcionarioLogado;
    private final FilmeService filmeService;
    private final FuncionarioService funcionarioService;
    private final ClienteService clienteService;
    private final SalaService salaService;
    private final SessaoService sessaoService;
    private final MenuFilme menuFilme;

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
        menuFilme = new MenuFilme(funcionarioLogado, scanner);
    }

   @Override
    public void exibirMenu() {
    int opcao;
    do {
        exibirCabecalho("Menu Funcionário");
        System.out.println("1. Gerenciar Filmes");
        if (funcionarioLogado.ehGerente()) {
            System.out.println("2. Gerenciar Clientes");
            System.out.println("3. Gerenciar Funcionários");
        }
        System.out.println("4. Criar Sala");
        System.out.println("5. Criar Sessão");
        System.out.println("6. Exibir todas as Sessões");
        System.out.println("0. Logout");
        System.out.print("Opção: ");

        opcao = lerOpcao(scanner);
        tratarOpcao(opcao);

    } while (opcao != 0);
}


    @Override
    protected void tratarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> gerenciarFilmes(); // futuramente você pode extrair isso
            case 2 -> {
                if (funcionarioLogado.ehGerente()) gerenciarClientes();
                else System.out.println("Acesso negado.");
            }
            case 3 -> {
                if (funcionarioLogado.ehGerente()) gerenciarFuncionarios();
                else System.out.println("Acesso negado.");
            }
            case 4 -> criarSala();
            case 5 -> criarSessao();
            case 6 -> visualizarSessoes();
            case 0 -> System.out.println("Logout realizado.");
            default -> System.out.println("Opção inválida.");
        }
    }

    // ---------------- MÉTODOS DE GERENCIAMENTO ----------------

   private void gerenciarFilmes() {
    int opcao;
    do {
        menuFilme.exibirMenu();
        opcao = lerOpcao(scanner);
        menuFilme.tratarOpcao(opcao);
    } while (opcao != 0);
    }


    // GERENCIAR CLIENTES
    private void gerenciarClientes() {
        int opcao;
        do {
            exibirCabecalho("Gerenciar Clientes");
            System.out.println("1. Listar todos os clientes");
            System.out.println("2. Editar cliente");
            System.out.println("3. Remover cliente");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = lerOpcao(scanner);

            switch (opcao) {
                case 1 -> listarClientes();
                case 2 -> editarCliente();
                case 3 -> removerCliente();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

        private void listarClientes() {
    try {
        var clientes = clienteService.obterTodosClientes();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        clientes.forEach(Cliente::exibirInformacoes);
    } catch (CampoInvalido e) {
        System.out.println("Erro: " + e.getMessage());
    }


    }

    private void editarCliente() {
        System.out.print("Email do cliente a editar: ");
        String email = scanner.nextLine();

        try {
            Cliente cliente = clienteService.buscarPorEmail(email);

            System.out.print("Novo nome (Enter para manter): ");
            String nome = scanner.nextLine();
            if (!nome.isBlank()) clienteService.editarNome(cliente, nome);

            System.out.print("Novo email (Enter para manter): ");
            String novoEmail = scanner.nextLine();
            if (!novoEmail.isBlank()) clienteService.editarEmail(cliente, novoEmail);

            System.out.print("Nova senha (Enter para manter): ");
            String senha = scanner.nextLine();
            if (!senha.isBlank()) clienteService.editarSenha(cliente, senha);

            System.out.println("Cliente atualizado com sucesso.");
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerCliente() {
        System.out.print("Email do cliente: ");
        String email = scanner.nextLine();
        try {
            Cliente cliente = clienteService.buscarPorEmail(email);
            clienteService.removerCliente(cliente);
            System.out.println("Cliente removido com sucesso.");
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // GERENCIAR FUNCIONÁRIOS
    private void gerenciarFuncionarios() {
        int opcao;
        do {
            exibirCabecalho("Gerenciar Funcionários");
            System.out.println("1. Listar todos");
            System.out.println("2. Editar");
            System.out.println("3. Adicionar");
            System.out.println("4. Remover");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = lerOpcao(scanner);

            switch (opcao) {
                case 1 -> listarFuncionarios();
                case 2 -> editarFuncionarios();
                case 3 -> cadastrarFuncionario();
                case 4 -> removerFuncionario();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void listarFuncionarios() {
        var funcionarios = funcionarioService.obterTodosClientes(); // talvez renomear esse método
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

        boolean gerente = false;
        while (true) {
            System.out.print("É gerente? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (resposta.equals("s")) {
                gerente = true;
                break;
            } else if (resposta.equals("n")) {
                break;
            } else {
                System.out.println("Resposta inválida.");
            }
        }

        try {
            Funcionario novo = new Funcionario(nome, email, senha, gerente);
            funcionarioService.cadastrarFuncionario(novo);
            System.out.println("Funcionário cadastrado com sucesso.");
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerFuncionario() {
        System.out.print("Email do funcionário: ");
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
        System.out.print("Email do funcionário: ");
        String email = scanner.nextLine();
        try {
            Funcionario funcionario = funcionarioService.buscarPorEmail(email);

            System.out.print("Novo nome (Enter para manter): ");
            String nome = scanner.nextLine();
            if (!nome.isBlank()) funcionarioService.editarNome(funcionario, nome);

            System.out.print("Novo email (Enter para manter): ");
            String novoEmail = scanner.nextLine();
            if (!novoEmail.isBlank()) funcionarioService.editarEmail(funcionario, novoEmail);

            System.out.print("Nova senha (Enter para manter): ");
            String senha = scanner.nextLine();
            if (!senha.isBlank()) funcionarioService.editarSenha(funcionario, senha);

            System.out.print("Novo status de gerente (s/n/Enter para manter): ");
            String status = scanner.nextLine().trim().toLowerCase();
            if (status.equals("s")) {
                funcionarioService.promoverOuRebaixarGerente(funcionario, true);
            } else if (status.equals("n")) {
                funcionarioService.promoverOuRebaixarGerente(funcionario, false);
            }

            System.out.println("Funcionário atualizado com sucesso.");
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // ---------------- SALA ----------------
    private void criarSala() {
        exibirCabecalho("Criar Sala");
        try {
            System.out.print("Número da sala: ");
            int numero = Integer.parseInt(scanner.nextLine());
            Sala novaSala = new Sala(numero);
            salaService.cadastrarSala(novaSala);
            System.out.println("Sala criada com sucesso.");
        } catch (NumberFormatException e) {
            System.out.println("Erro: número inválido.");
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // ---------------- SESSÃO ----------------
    private void criarSessao() {
        exibirCabecalho("Criar Sessão");
        try {
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

            System.out.print("Data e hora da sessão: ");
            String dataHora = scanner.nextLine();
            if (dataHora.isBlank()) {
                System.out.println("Data/hora inválida.");
                return;
            }

            Sessao sessao = new Sessao(filme, sala, dataHora);
            sessaoService.criarSessao(sessao);
            salaService.associarSessaoASala(sala, sessao);
            System.out.println("Sessão criada com sucesso.");
        } catch (NumberFormatException e) {
            System.out.println("Erro: número inválido.");
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    

    private void visualizarSessoes() {
        exibirCabecalho("Sessões Cadastradas");
        try {
            sessaoService.exibirSessoes();
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
