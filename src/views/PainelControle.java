package views;

import java.util.Scanner;

import repositories.RepositorioClientes;
import repositories.RepositorioFilmes;
import repositories.RepositorioFuncionarios;
import repositories.RepositorioSalas;
import repositories.RepositorioSessao;

public class PainelControle {

    private  RepositorioSessao repositorioSessoes;
    private  RepositorioClientes repositorioClientes;
    private  RepositorioFilmes repositorioFilmes;
    private  RepositorioFuncionarios repositorioFuncionario;
    private  RepositorioSalas repositorioSalas;
    private Scanner scanner;

    public PainelControle(RepositorioSessao repositorioSessoes, RepositorioClientes repositorioClientes,
                          RepositorioFilmes repositorioFilmes, RepositorioFuncionarios repositorioFuncionario,
                          RepositorioSalas repositorioSalas) {
        this.repositorioSessoes = repositorioSessoes;
        this.repositorioClientes = repositorioClientes;
        this.repositorioFilmes = repositorioFilmes;
        this.repositorioFuncionario = repositorioFuncionario;
        this.repositorioSalas = repositorioSalas;
        this.scanner = new Scanner(System.in);
    }

    //só pra ver
    public void menuLogin(){
        System.out.println("\n=== Bem-vindo à Bilheteria ===");
        System.out.println("1 - Entrar como Cliente");
        System.out.println("2 - Cadastrar-se como Cliente");
        System.out.println("3 - Entrar como Funcionário");
        System.out.println("4 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    //faz coisa
    public void processar() {
        boolean rodando = true;
        while (rodando) {
            menuLogin();
            String escolha = scanner.nextLine();
            switch (escolha) {
                case "1":
                    loginCliente();
                    break;
                case "2":
                    cadastrarCliente();
                    break;
                case "3":

                case "4":
                    System.out.println("Encerrando o sistema. Até logo!");
                    rodando = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void cadastrarCliente() {
        System.out.println("\n======== Cadastro Cliente ========");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        if (repositorioClientes.buscarporEmail(email) != null) {
            System.out.println("Já existe um cliente com esse email.");
            return;
        }

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        var novoCliente = new entities.Cliente(nome, email, senha);
        repositorioClientes.adicionarCliente(novoCliente);
        System.out.println("Cadastro realizado com sucesso!");
    }

    private void loginCliente() {
        System.out.println("\n======== Login Cliente ========");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        var cliente = repositorioClientes.buscarporEmail(email);
        if (cliente != null && cliente.getSenha().equals(senha)) {
            System.out.println("Login efetuado com sucesso, " + cliente.getNome() + "!");
            menuCliente(cliente);
        } else {
            System.out.println("Email ou senha inválidos.");
        }
    }

private void menuCliente(entities.Cliente cliente) {
        boolean clienteLogado = true;
        while (clienteLogado) {
            System.out.println("\n======== Menu Cliente ========");
            System.out.println("1 - Ver filmes em cartaz");
            System.out.println("2 - Comprar ingresso");
            System.out.println("3 - Ver ingressos comprados");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1":
                    //visualizarFilmes();
                    break;
                case "2":
                    //comprarIngresso(cliente);
                    break;
                case "3":
                    System.out.println("Saindo...");
                    clienteLogado = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
    
}
