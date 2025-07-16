package views;

import services.*;
import entities.*;
import exceptions.CampoInvalido;

import java.util.List;
import java.util.Scanner;

public class MenuCliente {
    private final Scanner scanner;
    private Cliente clienteLogado;
    private final SessaoService sessaoService;
    private final IngressoService ingressoService;
    private final MenuFilme menuFilme;

    public MenuCliente(
        Scanner scanner,
        Cliente clienteLogado,
        ClienteService clienteService,
        FilmeService filmeService,
        SessaoService sessaoService,
        SalaService salaService,
        IngressoService ingressoService,
        MenuFilme menuFilme
    ) {
        this.scanner = scanner;
        this.clienteLogado = clienteLogado;
        this.sessaoService = sessaoService;
        this.ingressoService = ingressoService;
        this.menuFilme = menuFilme;
        
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n===== Menu Cliente =====");
            System.out.println("1 - Comprar ingresso");
            System.out.println("2 - Visualizar filmes em cartaz");
            System.out.println("3 - Meus ingressos");
            System.out.println("0 - Logout");
            System.out.print("Escolha uma opção: ");

            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1: comprarIngresso(); break;
                case 2: menuFilme.mostrarFilmesEmCartaz(); break;
                case 3: exibirMeusIngressos(); break;
                case 0: if (clienteLogado == null) {
                    System.out.println("Erro: Nenhum cliente está logado.");
                    return;
                    }; 
                break;
                default: System.out.println("Opção inválida."); break;
            }
        } while (opcao != 0);
    }

private void comprarIngresso() {
    try {
        menuFilme.mostrarFilmesEmCartaz();

        System.out.print("Digite o nome do filme para escolher a sessão: ");
        String nomeFilme = scanner.nextLine();

        var sessoes = sessaoService.buscarSessoesPorFilme(nomeFilme);
        if (sessoes.isEmpty()) {
            System.out.println("Nenhuma sessão encontrada para esse filme.");
            return;
        }

        System.out.println("Selecione a sessão:");
        for (int i = 0; i < sessoes.size(); i++) {
            Sessao s = sessoes.get(i);
            System.out.printf("%d - Sala %d - Data: %s%n", i + 1, s.getSala().getNumeroSala(), s.getDataHora());
        }

        int opcaoSessao = Integer.parseInt(scanner.nextLine());
        if (opcaoSessao < 1 || opcaoSessao > sessoes.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        Sessao sessaoEscolhida = sessoes.get(opcaoSessao - 1);
        Sala sala = sessaoEscolhida.getSala();

        exibirMapaAssentos(sala);

        System.out.print("Digite as coordenadas dos assentos separadas por vírgula (ex: A1,B2,C3): ");
        String entrada = scanner.nextLine().toUpperCase();
        String[] cadeiras = entrada.split(",");

        for (String cadeira : cadeiras) {
            cadeira = cadeira.trim();
            Assento assentoEscolhido = sala.buscarAssento(cadeira);

            if (assentoEscolhido == null) {
                System.out.println("Assento " + cadeira + " inválido. Pulando...");
                continue;
            }
            if (assentoEscolhido.isOcupado()) {
                System.out.println("Assento " + cadeira + " já está ocupado. Pulando...");
                continue;
            }

            System.out.println("Tipos de ingresso disponíveis para o assento " + cadeira + ":");
            for (TipoIngresso tipo : TipoIngresso.values()) {
                System.out.printf("%d - %s%n", tipo.ordinal() + 1, tipo);
            }

            System.out.print("Digite o tipo de ingresso desejado: ");
            int tipo;
            try {
                tipo = Integer.parseInt(scanner.nextLine());
                if (tipo < 1 || tipo > TipoIngresso.values().length) {
                    System.out.println("Tipo de ingresso inválido para o assento " + cadeira + ". Pulando...");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida para o assento " + cadeira + ". Pulando...");
                continue;
            }

            Ingresso ingresso = ingressoService.comprarIngresso(
                clienteLogado,
                sessaoEscolhida,
                assentoEscolhido,
                TipoIngresso.values()[tipo - 1],
                10 
            );

            clienteLogado.adicionarIngresso(ingresso);
            System.out.println("Ingresso para o assento " + cadeira + " comprado com sucesso!");
        }

    } catch (CampoInvalido e) {
        System.out.println("Erro na compra: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.out.println("Entrada numérica inválida.");
    } catch (Exception e) {
        System.out.println("Erro inesperado: " + e.getMessage());
    }
}



    private void exibirMeusIngressos() {

        List<Ingresso> ingressos = clienteLogado.getIngressos();

        if (ingressos == null || ingressos.isEmpty()) {
            System.out.println("Você ainda não comprou nenhum ingresso.");
            return;
        }

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

    private void exibirMapaAssentos(Sala sala) {
        System.out.println("Sala " + sala.getNumeroSala() + " - Mapa de Assentos\n");

        int ocupados = 0;
        int colunas = 10;
        String[] fileiras = {"A", "B", "C", "D", "E"};

        System.out.print("    ");
        for (int col = 1; col <= colunas; col++) {
            System.out.printf("%-4d", col);
        }
        System.out.println();

        for (String linha : fileiras) {
            System.out.print(linha + ": ");
            for (int col = 1; col <= colunas; col++) {
                Assento assento = sala.buscarAssento(linha + col);
                boolean ocupado = assento != null && assento.isOcupado();
                System.out.print(ocupado ? "[X] " : "[ ] ");
                if (ocupado) ocupados++;
            }
            System.out.println();
        }

        int total = sala.getAssentos().size();
        System.out.println("\n[ ] = livre | [X] = ocupado");
        System.out.printf("Capacidade total: %d | Ocupados: %d | Livres: %d\n\n", total, ocupados, total - ocupados);
    }
}
