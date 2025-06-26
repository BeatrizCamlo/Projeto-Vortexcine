package views;

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

    public PainelControle(RepositorioSessao repositorioSessoes, RepositorioClientes repositorioClientes,
                          RepositorioFilmes repositorioFilmes, RepositorioFuncionarios repositorioFuncionario,
                          RepositorioSalas repositorioSalas) {
        this.repositorioSessoes = repositorioSessoes;
        this.repositorioClientes = repositorioClientes;
        this.repositorioFilmes = repositorioFilmes;
        this.repositorioFuncionario = repositorioFuncionario;
        this.repositorioSalas = repositorioSalas;
    }

    public void menuTipoUsuario(){
        
    }

    
}
