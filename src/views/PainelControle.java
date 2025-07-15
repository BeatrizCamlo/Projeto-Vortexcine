package views;

import java.util.Scanner;

import data.DataSeeder;
import entities.Cliente;
import entities.Funcionario;
import exceptions.CampoInvalido;
import services.*;
import repositories.*;

public class PainelControle {
    private final Scanner scanner = new Scanner(System.in);
    private final ClienteService clienteService = new ClienteService(new RepositorioClientes());
    private final FuncionarioService funcionarioService = new FuncionarioService(new RepositorioFuncionarios());
    private final FilmeService filmeService = new FilmeService(new RepositorioFilmes());
    private final SessaoService sessaoService = new SessaoService(new RepositorioSessao());
    private final SalaService salaService = new SalaService(new RepositorioSalas());
    private final IngressoService ingressoService = new IngressoService(new RepositorioIngressos());
    private final DataSeeder seeder = new DataSeeder(clienteService, filmeService, funcionarioService, salaService, sessaoService);

    public void mostrarRecepcao() {
        System.out.println("██╗   ██╗ ██████╗ ██████╗ ████████╗███████╗██╗   ██╗██████╗██╗███╗   ██╗███████╗");
        System.out.println("██║   ██║██╔═══██╗██   ██╗╚══██╔══╝██╔════╝██║   ██║██╔═══ ██║████╗  ██║██╔════╝");
        System.out.println("██║   ██║██║   ██║██ ███═╝   ██║   █████╗   ╚█████╔╝██║    ██║██╔██╗ ██║█████╗");
        System.out.println("██║   ██║██║   ██║██  ██     ██║   ██╔══╝  ██║   ██║██║    ██║██║╚██╗██║██╔══╝");
        System.out.println("╚██████╔╝╚██████╔╝██   ██    ██║   ███████╗██║   ██║██████╗██║██║ ╚████║███████╗");
        System.out.println(" ╚═════╝  ╚═════╝ ╚═════╝    ╚═╝   ╚══════╝╚═╝   ╚═╝╚═════╝╚═╝╚═╝  ╚═══╝╚══════╝");
        System.out.println();
        System.out.println("                     V O R T E X  C I N E  🎬");
        System.out.println("                   Bem-vindo ao seu universo de filmes!");
    }

    public void iniciarSistema() {
        mostrarRecepcao();
        seeder.carregarDados();
        loginTipoUsuario();
    }

    private void loginTipoUsuario() {
        while (true) {
            System.out.println("\n======== Faça Login ========");
            System.out.println("1 - Entrar como Cliente");
            System.out.println("2 - Cadastrar-se como Cliente");
            System.out.println("3 - Entrar como Funcionário");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            String entrada = scanner.nextLine();
            int opcao = entrada.matches("\\d+") ? Integer.parseInt(entrada) : -1;

            switch (opcao) {
                case 1: loginCliente(); break;
                case 2: cadastrarNovoCliente(); break;
                case 3: loginFuncionario(); break;
                case 0: {
                    System.out.println("Encerrando o sistema.");
                    return;
                }
                default: System.out.println("Opção inválida."); break;
            }
        }
    }

    private void cadastrarNovoCliente() {
        System.out.println("\n======== Cadastro Cliente ========");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            Cliente novoCliente = new Cliente(nome, email, senha);
            clienteService.cadastrarCliente(novoCliente);
            System.out.println("Cadastro realizado com sucesso!");
        } catch (CampoInvalido e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void loginCliente() {
    System.out.print("Email: ");
    String email = scanner.nextLine();
    System.out.print("Senha: ");
    String senha = scanner.nextLine();

    try {
        Cliente clienteLogado = clienteService.autenticar(email, senha);
        if (clienteLogado != null) {
            System.out.println("Login realizado com sucesso!");
            MenuCliente menuCliente = new MenuCliente(
                scanner,
                clienteLogado,
                clienteService,
                filmeService,
                sessaoService,
                salaService,
                ingressoService,
                new MenuFilme(clienteLogado, scanner)
            );
            menuCliente.exibirMenu();
        } else {
            System.out.println("Credenciais inválidas. Tente novamente.");
        }
    } catch (CampoInvalido e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

    private void loginFuncionario() {
    System.out.print("Email: ");
    String email = scanner.nextLine();
    System.out.print("Senha: ");
    String senha = scanner.nextLine();

    try {
        Funcionario funcionarioLogado = funcionarioService.autenticar(email, senha);
        System.out.println("Login realizado com sucesso!");
        MenuFuncionario menuFuncionario = new MenuFuncionario(
            scanner,
            funcionarioLogado,
            filmeService,
            funcionarioService,
            clienteService,
            salaService,
            sessaoService
        );
        menuFuncionario.exibirMenu();
    } catch (CampoInvalido e) {
        System.out.println("Erro: " + e.getMessage()); 
    }
    }
}