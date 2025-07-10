package views;

import java.util.Scanner;

import entities.Cliente;
import entities.Funcionario;
import repositories.*;

public class PainelControle {
    private final Scanner scanner = new Scanner(System.in);
    private final RepositorioClientes repositorioClientes = new RepositorioClientes();
    private final RepositorioFilmes repositorioFilmes = new RepositorioFilmes();
    private final RepositorioFuncionarios repositorioFuncionarios = new RepositorioFuncionarios();
    private final RepositorioSessao repositorioSessao = new RepositorioSessao();
    private final RepositorioSalas repositorioSalas = new RepositorioSalas();

    public PainelControle() {
        repositorioSalas.inicializarAssentos();
        
    }

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

        repositorioFuncionarios.inicializarGerentePadrao();
        repositorioFilmes.popularFilmesIniciais();
    }

    public void iniciarSistema() {
        mostrarRecepcao();
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
                case 2: cadastrarCliente(); break;
                case 3: loginFuncionario(); break;
                case 0: {
                    System.out.println("Encerrando o sistema.");
                    return;
                }
                default: System.out.println("Opção inválida."); break;
            }
        }
    }

    private void cadastrarCliente() {
        System.out.println("\n======== Cadastro Cliente ========");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (repositorioClientes.obterPorEmail(email) != null) {
            System.out.println("Já existe um cliente com esse email.");
            return;
        }


        Cliente novoCliente = new Cliente(nome, email, senha);
        repositorioClientes.cadastrar(novoCliente);

        System.out.println("Cadastro realizado com sucesso!");
    }

    private void loginCliente() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Cliente clienteLogado = repositorioClientes.autenticarCliente(email, senha);
        if (clienteLogado != null) {
            System.out.println("Login realizado com sucesso!");
            MenuCliente menuCliente = new MenuCliente(scanner, repositorioClientes, repositorioFilmes, repositorioSessao, repositorioSalas);
            menuCliente.exibirMenu();
        } else {
            System.out.println("Credenciais inválidas.");
        }
    }

    private void loginFuncionario() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Funcionario funcionarioLogado = repositorioFuncionarios.autenticarFuncionario(email, senha);
        if (funcionarioLogado != null) {
            System.out.println("Login realizado com sucesso!");
            MenuFuncionario menuFuncionario = new MenuFuncionario(
                scanner,
                funcionarioLogado,
                repositorioFilmes,
                repositorioFuncionarios,
                repositorioClientes
            );
            menuFuncionario.exibirMenu();
        } else {
            System.out.println("Credenciais inválidas.");
        }
    }
}