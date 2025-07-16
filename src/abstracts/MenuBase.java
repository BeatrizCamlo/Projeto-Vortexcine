package abstracts;

import java.util.Scanner;

public abstract class MenuBase {
    protected static final Scanner scanner = new Scanner(System.in);

    public abstract void exibirMenu();

    protected abstract void tratarOpcao(int opcao);

    
    public void iniciar() {
        int opcao;
        do {
            exibirMenu();
            opcao = obterEntradaUsuario();
            tratarOpcao(opcao);
        } while (!deveSair(opcao));
    }

    protected int obterEntradaUsuario() {
        while (!scanner.hasNextInt()) {
            System.out.print("Digite uma opção válida (número): ");
            scanner.next(); 
        }
        int opcao = scanner.nextInt();
        scanner.nextLine(); 
        return opcao;
    }

    public int lerOpcao(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Digite uma opção válida (número): ");
            scanner.next();
        }
        int opcao = scanner.nextInt();
        scanner.nextLine(); 
        return opcao;
    }

    protected boolean deveSair(int opcao) {
        return opcao == 0;
    }

    public void exibirCabecalho(String titulo) {
        System.out.println("\n===== " + titulo + " =====");
    }
}
