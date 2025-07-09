package views;

import repositories.*;
import entities.*;

import java.util.Scanner;

public class MenuCliente {
    private final Scanner scanner;
    private final RepositorioClientes repositorioClientes;
    private final RepositorioFilmes repositorioFilmes;
    private final RepositorioSessao repositorioSessoes;
    private final RepositorioSalas repositorioSalas;

    private Cliente clienteLogado;

    public MenuCliente(
        Scanner scanner,
        RepositorioClientes repositorioClientes,
        RepositorioFilmes repositorioFilmes,
        RepositorioSessao repositorioSessoes,
        RepositorioSalas repositorioSalas
    ) {
        this.scanner = scanner;
        this.repositorioClientes = repositorioClientes;
        this.repositorioFilmes = repositorioFilmes;
        this.repositorioSessoes = repositorioSessoes;
        this.repositorioSalas = repositorioSalas;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n====== Menu Cliente ======");
            System.out.println("1. Comprar Ingresso");
            System.out.println("2. Visualizar Filmes em Cartaz");
            System.out.println("3. Meus Ingressos");
            System.out.println("0. Deslogar");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1: comprarIngresso(); break;
                case 2: mostrarFilmesEmCartaz(); break;
                case 3: exibirMeusIngressos(); break;
                case 0: clienteLogado = null; break;
                default: System.out.println("Opção inválida."); break;
            }
        } while (opcao != 0);
    }

    public void cadastrar() {
        System.out.println("\n======== Cadastro Cliente ========");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (repositorioClientes.buscarporEmail(email) != null) {
            System.out.println("Já existe um cliente com esse email.");
            return;
        }

        Cliente cliente = new Cliente(nome, email, senha);
        repositorioClientes.adicionarCliente(cliente);
        System.out.println("Cadastro realizado com sucesso!");
    }

    private void comprarIngresso() {
        System.out.println("==== Compra de Ingresso ====");
        // mostrarAssentos();
        //reservarAssento();
    }

    /* 
    private void mostrarAssentos() {
        char[][] assentos = repositorioSalas.getAssentosSalaPadrao();
        System.out.println("\nAssentos (A = livre, X = ocupado):");
        System.out.print("    ");
        for (int j = 1; j <= 10; j++) {
            System.out.printf("%-4d", j);
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            char letraLinha = (char) ('A' + i);
            System.out.print(letraLinha + "   ");
            for (int j = 0; j < 10; j++) {
                System.out.print("[" + assentos[i][j] + "] ");
            }
            System.out.println();
        }
    }
    */
    /* 
    private void reservarAssento() {
        char[][] assentos = repositorioSalas.getAssentosSalaPadrao();

        System.out.print("Digite a linha (A-J): ");
        String linhaStr = scanner.next().toUpperCase();
        System.out.print("Digite a coluna (1-10): ");
        int coluna = scanner.nextInt();
        scanner.nextLine();

        int linha = linhaStr.charAt(0) - 'A';
        int colunaIndex = coluna - 1;

        if (assentos[linha][colunaIndex] == 'A') {
            assentos[linha][colunaIndex] = 'X';
            System.out.println("Assento " + linhaStr + coluna + " reservado com sucesso!");
        } else {
            System.out.println("Assento já ocupado.");
        }
    }
    */

    private void mostrarFilmesEmCartaz() {
        System.out.println(" ====== Filmes em Exibição ======");
        var filmes = repositorioFilmes.obterTodosFilmes();
        if (filmes.isEmpty()) {
            System.out.println("Nenhum filme encontrado.");
        } else {
            filmes.forEach(Filme::exibirInformacoes);
        }
    }

    private void exibirMeusIngressos() {
        System.out.println("==== Ingressos do Cliente ====");
        for (Ingresso ingresso : clienteLogado.getIngressos()) {
            System.out.println("-------------------------");
            System.out.println("Filme: " + ingresso.getSessao().getFilme().getNome());
            System.out.println("Data: " + ingresso.getSessao().getDataHora());
            System.out.println("Sala: " + ingresso.getSessao().getSala().getNumeroSala());
            System.out.println("Assento: " + ingresso.getAssento());
            System.out.println("-------------------------");
        }
    }
}
