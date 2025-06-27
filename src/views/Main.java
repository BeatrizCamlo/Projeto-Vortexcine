package views;

import views.PainelControle;
import repositories.*;

public class Main {
    public static void main(String[] args) {

        RepositorioClientes repositorioClientes = new RepositorioClientes();
        RepositorioFilmes repositorioFilmes = new RepositorioFilmes();
        RepositorioFuncionarios repositorioFuncionarios = new RepositorioFuncionarios();
        RepositorioSalas repositorioSalas = new RepositorioSalas();
        RepositorioSessao repositorioSessoes = new RepositorioSessao();

        PainelControle painel = new PainelControle(
            repositorioSessoes,
            repositorioClientes,
            repositorioFilmes,
            repositorioFuncionarios,
            repositorioSalas
        );

        painel.processar();
    }
}
