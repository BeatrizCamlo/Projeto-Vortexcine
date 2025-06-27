package views;
import java.util.Scanner;
import entities.*;
import repositories.*;


public class PainelControle {
    private RepositorioSessao repositorioSessoes;
    private RepositorioClientes repositorioClientes;
    private RepositorioFilmes repositorioFilmes;
    private RepositorioFuncionarios repositorioFuncionario;
    private RepositorioSalas repositorioSalas;
    private Scanner scanner = new Scanner(System.in);
    private Cliente clienteLogado;
    private Funcionario funcionarioLogado;


    public PainelControle() {
        repositorioClientes = new RepositorioClientes();
        repositorioFilmes = new RepositorioFilmes();
        repositorioFuncionario = new RepositorioFuncionarios();
        repositorioSessoes = new RepositorioSessao();
        repositorioSalas = new RepositorioSalas();
        repositorioSalas.inicializarAssentos();
    }

    public void mostrarRecepcao() {
        System.out.println("Bem-vindo ao VortexCine!");
        loginTipoUsuario();
    }

    public void loginTipoUsuario() {
        while (true) {
            System.out.println("Escolha o tipo de usuário:");
            System.out.println("1. Funcionário");
            System.out.println("2. Cliente");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            processarEscolhaUsuario(opcao);
        }
    }

    public void processarEscolhaUsuario(int opcao) {
        switch (opcao) {
            case 1: loginFuncionario(); break;
            case 2: loginCliente(); break;
            case 0: System.out.println("Encerrando o sistema."); System.exit(0); break;
            default: System.out.println("Opção inválida.");
        }
    }

    public void loginFuncionario() {
        System.out.print("Email: ");
        String email = scanner.next();
        System.out.print("Senha: ");
        String senha = scanner.next();
        funcionarioLogado = repositorioFuncionario.autenticarFuncionario(email, senha);
        if (funcionarioLogado != null) {
            System.out.println("Login bem-sucedido!");
            menuFuncionario();
        } else {
            System.out.println("Credenciais inválidas.");
        }
    }

    public void menuFuncionario() {
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
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1: cadastrarFilme(); break;
                case 2: atualizarFilme(); break;
                case 3: visualizarFilmes(); break;
                case 4: deletarFilmes(); break;
                case 0: funcionarioLogado = null; break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public void cadastrarFilme() {
        System.out.println("====== Cadastrar Filme ======");
        System.out.print("Nome do Filme: ");
        scanner.nextLine(); 
        String nome = scanner.nextLine();
        System.out.print("Duração (em minutos): ");
        int duracao = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Gênero: ");
        String generoStr = scanner.nextLine();
        GeneroFilme genero = null;
        for (GeneroFilme g : GeneroFilme.values()) {
            if (g.name().equalsIgnoreCase(generoStr)) {
                genero = g;
                break;
            }
        }
        if (genero == null) {
            System.out.println("Gênero inválido. Cadastro cancelado.");
            return;
        }
        Filme filme = new Filme(nome, duracao, genero);
        repositorioFilmes.adicionarFilme(funcionarioLogado, filme);
        System.out.println("Filme cadastrado com sucesso!");
    }

    public void atualizarFilme() {
        System.out.println("====== Atualizar Filme ======");
        System.out.print("Digite o nome do filme a ser atualizado: ");
        String nome = scanner.next();
        Filme filme = repositorioFilmes.obterFilmePorNome(nome);
        System.out.println("Novo nome do filme: ");
        scanner.nextLine();
        String novoNome = scanner.nextLine();
        System.out.print("Novo gênero: ");
        String novoGeneroStr = scanner.nextLine();
        GeneroFilme novoGenero = null;
        for (GeneroFilme g : GeneroFilme.values()) {
            if (g.name().equalsIgnoreCase(novoGeneroStr)) {
                novoGenero = g;
                break;
            }
        }
        System.out.print("Nova duração: ");
        int novaDuracao = scanner.nextInt();
        repositorioFilmes.modificarNomeFilme(funcionarioLogado, filme, novoNome);
        repositorioFilmes.modificarDuracaoFilme(funcionarioLogado, filme, novaDuracao);
        repositorioFilmes.modificarGeneroFilme(funcionarioLogado, filme, novoGenero);
        System.out.println("Filme atualizado com sucesso.");
    }

    public void visualizarFilmes() {
        System.out.println(" ====== Filmes cadastrados ======");
        repositorioFilmes.obterTodosFilmes().forEach(filme -> {
            System.out.println("Nome: " + filme.getNome());
            System.out.println("Duração: " + filme.getDuracaoEmMinutos() + " min");
            System.out.println("Gênero: " + filme.getGenero());
            System.out.println("-------------------------");
        });
    }

    public void deletarFilmes() {
        System.out.println("====== Deletar Filme ======");
        System.out.print("Digite o nome do filme a ser removido: ");
        String nome = scanner.next();
        if(repositorioFilmes.obterFilmePorNome(nome) != null) {
            Filme filme = repositorioFilmes.obterFilmePorNome(nome);
            repositorioFilmes.removerFilme(funcionarioLogado, filme);
            System.out.println("Filme removido com sucesso.");
        } else {
            System.out.println("Filme não encontrado.");
        }
    }

    public void loginCliente() {
        System.out.print("Email: ");
        String email = scanner.next();
        System.out.print("Senha: ");
        String senha = scanner.next();
        clienteLogado = repositorioClientes.autenticarCliente(email, senha);
        if (clienteLogado != null) {
            System.out.println("Login realizado com sucesso!");
            mostrarFilmesEmCartaz();
            menuCliente();
        } else {
            System.out.println("Credenciais inválidas.");
        }
    }

    public void mostrarFilmesEmCartaz() {
        repositorioSessoes.exibirSessoes();
    }

    public void menuCliente() {
        int opcao;
        do {
            System.out.println("\n ====== Menu Cliente ======");
            System.out.println("1. Comprar Ingresso");
            System.out.println("2. Visualizar Filmes em Cartaz");
            System.out.println("3. Meus Ingressos");
            System.out.println("0. Deslogar");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            switch (opcao) {
                case 1: comprarIngresso(); break;
                case 2: mostrarFilmesEmCartaz(); break;
                case 3: exibirMeusIngressos(); break;
                case 0: clienteLogado = null; break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public void comprarIngresso() {
        System.out.println("==== Compra de Ingresso ====");
        mostrarAssentos();
        reservarAssento();
    }

    public void mostrarAssentos() {
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

    public void reservarAssento() {
        char[][] assentos = repositorioSalas.getAssentosSalaPadrao();
        System.out.println("\nEscolha um assento para reservar.");
        System.out.print("Digite a linha (A-J): ");
        String linhaStr = scanner.next().toUpperCase();
        if (linhaStr.length() != 1 || linhaStr.charAt(0) < 'A' || linhaStr.charAt(0) > 'J') {
            System.out.println("Linha inválida.");
            return;
        }
        System.out.print("Digite a coluna (1-10): ");
        int coluna = scanner.nextInt();
        if (coluna < 1 || coluna > 10) {
            System.out.println("Coluna inválida.");
            return;
        }
        int linha = linhaStr.charAt(0) - 'A';
        int colunaIndex = coluna - 1;
        if (assentos[linha][colunaIndex] == 'A') {
            assentos[linha][colunaIndex] = 'X';
            System.out.println("Assento " + linhaStr + coluna + " reservado com sucesso!");
        } else {
            System.out.println("Assento " + linhaStr + coluna + " já está ocupado.");
        }
    }

    public void exibirMeusIngressos() {
        System.out.println("==== Ingressos ====");
        clienteLogado.getIngressos().forEach(ingresso -> {
            System.out.println("-------------------------");
            System.out.println("Filme: " + ingresso.getSessao().getFilme().getNome());
            System.out.println("Data: " + ingresso.getSessao().getDataHora());
            System.out.println("Sala: " + ingresso.getSessao().getSala().getNumeroSala());
            System.out.println("Assento: " + ingresso.getAssento());
            System.out.println("-------------------------");
        });
    }

}
