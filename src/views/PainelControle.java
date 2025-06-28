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
        System.out.println("██╗   ██╗ ██████╗ ██████╗ ████████╗███████╗██╗   ██╗██████╗██╗███╗   ██╗███████╗");
        System.out.println("██║   ██║██╔═══██╗██   ██╗╚══██╔══╝██╔════╝██║   ██║██╔═══ ██║████╗  ██║██╔════╝");
        System.out.println("██║   ██║██║   ██║██ ███═╝   ██║   █████╗   ╚█████╔╝██║    ██║██╔██╗ ██║█████╗");
        System.out.println("██║   ██║██║   ██║██  ██     ██║   ██╔══╝  ██║   ██║██║    ██║██║╚██╗██║██╔══╝");
        System.out.println("╚██████╔╝╚██████╔╝██   ██    ██║   ███████╗██║   ██║██████╗██║██║ ╚████║███████╗");
        System.out.println(" ╚═════╝  ╚═════╝ ╚═════╝    ╚═╝   ╚══════╝╚═╝   ╚═╝╚═════╝╚═╝╚═╝  ╚═══╝╚══════╝");
        System.out.println();
        System.out.println("                     V O R T E X  C I N E  🎬");
        System.out.println("                   Bem-vindo ao seu universo de filmes!");

        repositorioFuncionario.inicializarGerentePadrao();
        repositorioFilmes.popularFilmesIniciais();
        loginTipoUsuario();
    }

    public void loginTipoUsuario() {
        while (true) {
            System.out.println("\n======== Faça Login ========");
            System.out.println("1 - Entrar como Cliente");
            System.out.println("2 - Cadastrar-se como Cliente");
            System.out.println("3 - Entrar como Funcionário");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(scanner.nextLine());
            processarEscolhaUsuario(opcao);
        }
    }

    public void processarEscolhaUsuario(int opcao) {
        switch (opcao) {
            case 1: loginCliente(); break;
            case 2: cadastrarCliente(); break;
            case 3: loginFuncionario(); break;
            case 0: System.out.println("Encerrando o sistema."); System.exit(0); break;
            default: System.out.println("Opção inválida.");
        }
    }

    // Parte do cliente:
    private void cadastrarCliente() {
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

        var novoCliente = new entities.Cliente(nome, email, senha);
        repositorioClientes.adicionarCliente(novoCliente, funcionarioLogado);
        System.out.println("Cadastro realizado com sucesso!");
    }

    public void loginCliente() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        clienteLogado = repositorioClientes.autenticarCliente(email, senha);
        if (clienteLogado != null) {
            System.out.println("Login realizado com sucesso!");
            visualizarFilmes();
            menuCliente();
        } else {
            System.out.println("Credenciais inválidas.");
        }
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

    public void loginFuncionario() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
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
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1: cadastrarFilme(); break;
                case 2: atualizarFilme(); break;
                case 3: visualizarFilmes(); break;
                case 4: deletarFilmes(); break;
                case 5: if (funcionarioLogado.ehGerente()) gerenciarClientes(); break;
                case 6: ; break;
                case 0: funcionarioLogado = null; break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public void gerenciarClientes() {
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

    public void gerenciarFuncionarios(){

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

    public void mostrarFilmesEmCartaz() {
        repositorioSessoes.exibirSessoes();
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
        var filmes = repositorioFilmes.obterTodosFilmes();
        if (filmes.isEmpty()) {
            System.out.println("Nenhum filme cadastrado.");
        } else {
            filmes.forEach(Filme::exibirInformacoes);
        }
    }

    public void listarClientes() {
        var clientes = repositorioClientes.obterTodosClientes();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        } else {
            clientes.forEach(Cliente::exibirInformacoes);
        }
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

    private void removerCliente() {
        System.out.println("====== Remover Cliente ======");
        System.out.print("Digite o email do cliente a ser removido: ");
        String email = scanner.nextLine();

        Cliente cliente = repositorioClientes.buscarporEmail(email);
        if (cliente != null) {
            repositorioClientes.removerCliente(cliente, funcionarioLogado);
            System.out.println("Cliente removido com sucesso.");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private void editarCliente() {
        System.out.println("====== Editar Cliente ======");
        System.out.print("Digite o email do cliente a ser editado: ");
        String email = scanner.next();

        Cliente cliente = repositorioClientes.buscarporEmail(email);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        scanner.nextLine();

        System.out.print("Novo nome (pressione Enter para manter atual): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isBlank()) {
            repositorioClientes.modificarNomeCliente(cliente, novoNome, funcionarioLogado);
        }

        System.out.print("Novo email (pressione Enter para manter atual): ");
        String novoEmail = scanner.nextLine();
        if (!novoEmail.isBlank()) {
            repositorioClientes.modificarEmailCliente(cliente, novoEmail, funcionarioLogado);
        }

        System.out.print("Nova senha (pressione Enter para manter atual): ");
        String novaSenha = scanner.nextLine();
        if (!novaSenha.isBlank()) {
            repositorioClientes.modificarSenhaCliente(cliente, novaSenha, funcionarioLogado);
        }

        System.out.println("Cliente atualizado com sucesso!");
    }
}
