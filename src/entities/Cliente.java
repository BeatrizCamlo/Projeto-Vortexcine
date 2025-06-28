package entities;

import java.util.List;
import java.util.ArrayList;

public class Cliente extends Pessoa {
    private List<Ingresso> ingressos;

    public Cliente(String nome, String email, String senha){
        super(nome, email, senha);
        this.ingressos = new ArrayList<>();
    }

    public List<Ingresso> getIngressos() {
        return ingressos;
    }

    public void adicionarIngresso(Ingresso ingresso){
        ingressos.add(ingresso);
    }

    public void listarIngressos(){
        for(int i = 0; i <= ingressos.size(); i++){
            System.err.println("- " + ingressos.get(i));
        }
    }

    public void exibirInformacoes(){
        super.exibirInformacoes();
    }

}
