package entities;

public abstract class Pessoa {
    private static int proximoId = 1;

    protected int id;
    protected String nome;
    protected String email;
    protected String senha;

    public Pessoa(String nome, String email, String senha){
        this.id = proximoId++;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void exibirInformacoes() {
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.printf("║ %-5s│ %-15s│ %-30s ║%n", 
            "ID: " + id, "Nome: " + nome, "Email: " + email);
        System.out.println("╚═══════════════════════════════════════════════════════╝");
    }
}
