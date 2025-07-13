package abstracts;

import java.util.Scanner;

public abstract class MenuBase {
    protected static final Scanner scanner = new Scanner(System.in);

    // Exibe o menu (deve ser sobrescrito)
    public abstract void exibirMenu();

    // Trata a opção (deve ser sobrescrito)
    protected abstract void tratarOpcao(int opcao);

    // Loop principal do menu
    public void iniciar() {
        int opcao;
        do {
            exibirMenu();
            opcao = obterEntradaUsuario();
            tratarOpcao(opcao);
        } while (!deveSair(opcao));
    }

    // Método auxiliar para ler a entrada do usuário com validação
    protected int obterEntradaUsuario() {
        while (!scanner.hasNextInt()) {
            System.out.print("Digite uma opção válida (número): ");
            scanner.next(); // descarta entrada inválida
        }
        int opcao = scanner.nextInt();
        scanner.nextLine(); // limpa buffer
        return opcao;
    }

    // Método reutilizável em outros menus (como MenuCliente)
    public int lerOpcao(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Digite uma opção válida (número): ");
            scanner.next();
        }
        int opcao = scanner.nextInt();
        scanner.nextLine(); // limpa buffer
        return opcao;
    }

    // Define quando sair do menu (padrão: quando for 0)
    protected boolean deveSair(int opcao) {
        return opcao == 0;
    }

    // Exibe cabeçalho com título
    public void exibirCabecalho(String titulo) {
        System.out.println("\n===== " + titulo + " =====");
    }
}
