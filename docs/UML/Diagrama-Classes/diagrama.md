classDiagram

subgraph Entidades
    direction TB
    class Pessoa {
      <<abstract>>
      - int id
      - String nome
      - String email
      - String senha
    }

    class Cliente {
      - List~Ingresso~ ingressos
    }

    class Funcionario {
      - boolean ehGerente
    }

    class Filme {
      - String nome
      - int duracaoEmMinutos
      - GeneroFilme genero
    }

    class Sala {
      - int numeroSala
      - List~Assento~ assentos
      - List~Sessao~ sessoes
    }

    class Sessao {
      - Filme filme
      - Sala sala
      - LocalDateTime horarioInicio
      - List~Cliente~ publico
      - List~Assento~ assentosOcupados
    }

    class Ingresso {
      - Sessao sessao
      - Assento assento
      - Cliente cliente
      - TipoIngresso tipo
      - double preco
    }

    class Assento {
      - Coordenada coordenada
      - boolean ocupado
    }

    class Coordenada {
      - String linha
      - int coluna
    }
end

Pessoa <|-- Cliente
Pessoa <|-- Funcionario

Cliente --> "*" Ingresso
Ingresso --> Sessao
Ingresso --> Cliente
Ingresso --> Assento
Sessao --> Filme
Sessao --> Sala
Sessao --> "0..*" Cliente
Sessao --> "0..*" Assento
Sala --> "*" Assento
Sala --> "*" Sessao
Assento --> Coordenada


subgraph Abstratos
    direction TB

    class RepositorioInterface {
      <<abstract>>
      + void cadastrar(T entidade)
      + void remover(T entidade)
      + void editarNome(T entidade)
      + void editarEmail(T entidade)
      + void editarSenha(T entidade)
      + T obterPorNome(String nome)
      + T obterPorId(int id)
      + T obterPorEmail(String Email)
    }

    class MenuBase {
      <<abstract>>
      + void LerOpcao()
      + void exibirMenu()
      + void exibirCabecalho(String titulo)
    }
end


subgraph Repositorios
    direction TB

    class RepositorioAssentos {
        - HashMap<Cliente, Assento> assentosOcupados
        + void adicionar(Cliente, Assento)
        + boolean existeReserva(Cliente)
        + boolean assentoJaReservado(Assento)
        + Assento remover(Cliente)
        + Assento obterAssentoPorCliente(Cliente)
        + HashMap<Cliente, Assento> listarReservas()
        + void limparTudo()
    }

    class RepositorioClientes {
        - List<Cliente> clientes
        - HashMap<String, Cliente> clientesPorEmail
        + RepositorioClientes()
        + void cadastrar(Cliente)
        + void remover(Cliente)
        + void editarNome(Cliente, String)
        + void editarEmail(Cliente, String)
        + void editarSenha(Cliente, String)
        + Cliente obterPorNome(String)
        + Cliente obterPorId(int)
        + Cliente obterPorEmail(String)
        + List<Cliente> obterTodos()
    }

    class RepositorioFilmes {
        - List<Filme> filmes
        + void adicionar(Filme)
        + void remover(Filme)
        + void obterPorNome(Filme)
        + void editarNome(Filme)
        + void editarDuracao(filme)
        + void editarGenero(filme)
        + List<Filme> obterTodos()
    }

    class RepositorioFuncionarios {
        - List<Funcionario> funcionariosAtivos
        - HashMap<String, Funcionario> funcionariosPorEmail
        + void cadastrar(Funcionario)
        + void remover(Funcionario)
        + void editarNome(Funcionario, String)
        + void editarEmail(Funcionario, String)
        + void editarSenha(Funcionario, String)
        + Funcionario obterPorNome(String)
        + Funcionario obterPorId(int)
        + Funcionario obterPorEmail(String)
        + List<Funcionario> obterTodosFuncionarios()
        + void modificarStatusGerente(Funcionario, boolean)
    }

    class RepositorioIngressos {
        - List<Ingresso> ingressos
        + RepositorioIngressos()
        + void adicionar(Ingresso)
        + List<Ingresso> buscarPorCliente(Cliente)
        + List<Ingresso> listarTodos()
    }

    class RepositorioSalas {
        - List<Sala> salasTotais
        - HashMap<Sessao, Sala> salasPorSessao
        + RepositorioSalas()
        + void adicionar(Sala)
        + void associarSessao(Sala, Sessao)
        + Sala buscarPorSessao(Sessao)
        + boolean contem(Sala)
        + List<Sala> obterTodas()
        + boolean sessaoJaAssociada(Sessao)
        + Sala buscarPorNumero(int)
    }

    class RepositorioSessao {
        - List<Sessao> sessoesDisponiveis
        - HashMap<String, List<Sessao>> sessoesPorFilme
        + RepositorioSessao()
        + List<Sessao> obterTodasSessoes()
        + boolean existeSessao(Sessao)
        + void adicionarSessao(Sessao)
        + List<Sessao> buscarSessoesPorFilme(String)
    }
end

RepositorioClientes ..|> RepositorioInterface
RepositorioClientes --> Cliente
RepositorioAssentos --> Cliente
RepositorioAssentos --> Assento
RepositorioFilmes --> Filme
RepositorioFuncionarios ..|> RepositorioInterface
RepositorioFuncionarios --> Funcionario
RepositorioIngressos --> Ingresso
RepositorioIngressos --> Cliente
RepositorioSalas --> Sala
RepositorioSalas --> Sessao
RepositorioSessao --> Sessao
Sessao --> Filme

subgraph Servico
    class AssentoService {
        - RepositorioAssentos repositorio
        + AssentoService(RepositorioAssentos)
        + void reservarAssento(Cliente, Assento)
        + void cancelarReserva(Cliente)
        + boolean verificarDisponibilidade(Assento)
    }

    class ClienteService {
        - RepositorioClientes repositorio
        - static Set<String> DOMINIOS_ACEITOS
        + ClienteService(RepositorioClientes)
        - void validarEmail(String)
        + void cadastrarCliente(Cliente)
        + void removerCliente(Cliente)
        + void editarNome(Cliente, String)
        + void editarEmail(Cliente, String)
        + void editarSenha(Cliente, String)
        + Cliente autenticar(String, String)
        + Cliente buscarPorEmail(String)
        + List~Cliente~ obterTodosClientes()
    }

    class FuncionarioService {
        - RepositorioFuncionarios repositorio
        - static String dominio_aceito
        + FuncionarioService(RepositorioFuncionarios)
        - void validarEmail(String)
        + void cadastrarFuncionario(Funcionario)
        + Funcionario autenticar(String, String)
        + void editarNome(Funcionario, String)
        + void editarEmail(Funcionario, String)
        + void editarSenha(Funcionario, String)
        + void promoverOuRebaixarGerente(Funcionario, boolean)
        + void removerFuncionario(Funcionario)
        + Funcionario buscarPorEmail(String)
        + List~Funcionario~ obterTodosClientes()
    }

    class IngressoService {
        - RepositorioIngressos repositorio
        + IngressoService(RepositorioIngressos)
        + Ingresso comprarIngresso(Cliente, Sessao, Assento, TipoIngresso, double)
        + List~Ingresso~ listarIngressosPorCliente(Cliente)
    }

    class SalaService {
        - RepositorioSalas repositorio
        + SalaService(RepositorioSalas)
        + void cadastrarSala(Sala)
        + void associarSessaoASala(Sala, Sessao)
        + Sala buscarSalaPorSessao(Sessao)
        + Sala buscarSalaPorNumero(int)
    }

    class SessaoService {
        - RepositorioSessao repositorio
        + SessaoService(RepositorioSessao)
        + void criarSessao(Sessao)
        + void alterarSalaSessao(Sessao, Sala)
        + void alterarFilmeSessao(Sessao, Filme)
        + void alterarDataSessao(Sessao, String)
        + void exibirSessoes()
        + List~Sessao~ buscarSessoesPorFilme(String)
        + void exibirSessoesPorFilme(String)
    }

    class FilmeService {
        - RepositorioFilmes repositorio
        + FilmeService(RepositorioFilmes)
        + void adicionarFilme(Funcionario, Filme)
        + void removerFilme(Funcionario, Filme)
        + void modificarNome(Funcionario, Filme, String)
        + void modificarDuracao(Funcionario, Filme, int)
        + void modificarGenero(Funcionario, Filme, GeneroFilme)
        + List~Filme~ obterTodosFilmes()
        + Filme obterPorNome(String)
    }
end

AssentoService --> RepositorioAssentos
AssentoService ..> Cliente
AssentoService ..> Assento

ClienteService --> RepositorioClientes
ClienteService ..> Cliente

FuncionarioService --> RepositorioFuncionarios
FuncionarioService ..> Funcionario

IngressoService --> RepositorioIngressos
IngressoService ..> Ingresso
IngressoService ..> Cliente
IngressoService ..> Assento
IngressoService ..> Sessao
IngressoService ..> TipoIngresso

SalaService --> RepositorioSalas
SalaService ..> Sala
SalaService ..> Sessao

SessaoService --> RepositorioSessao
SessaoService ..> Sessao
SessaoService ..> Sala
SessaoService ..> Filme

FilmeService --> RepositorioFilmes
FilmeService ..> Filme
FilmeService ..> Funcionario
FilmeService ..> GeneroFilme
FilmeService ..> CampoInvalido

subgraph Viewers
    class Main {
        + main(String[]): void
    }

    class MenuCliente {
        - Scanner scanner
        - Cliente clienteLogado
        - SessaoService sessaoService
        - IngressoService ingressoService
        - MenuFilme menuFilme

        + MenuCliente(...)
        + void exibirMenu()
        - void comprarIngresso()
        - void exibirMeusIngressos()
        - void exibirMapaAssentos(Sala)
    }

    class PainelControle {
        - Scanner scanner
        - ClienteService clienteService
        - FuncionarioService funcionarioService
        - FilmeService filmeService
        - SessaoService sessaoService
        - SalaService salaService
        - IngressoService ingressoService
        - DataSeeder seeder

        + void mostrarRecepcao()
        + void iniciarSistema()
        - void loginTipoUsuario()
        - void cadastrarNovoCliente()
        - void loginCliente()
        - void loginFuncionario()
    }

    class MenuFuncionario {
        - Scanner scanner
        - Funcionario funcionarioLogado
        - FilmeService filmeService
        - FuncionarioService funcionarioService
        - ClienteService clienteService
        - SalaService salaService
        - SessaoService sessaoService
        - MenuFilme menuFilme

        + MenuFuncionario(...)
        + void exibirMenu()
        # void tratarOpcao(int)

        - void gerenciarFilmes()
        - void gerenciarClientes()
        - void listarClientes()
        - void editarCliente()
        - void removerCliente()
        - void gerenciarFuncionarios()
        - void listarFuncionarios()
        - void cadastrarFuncionario()
        - void removerFuncionario()
        - void editarFuncionarios()
        - void criarSala()
        - void criarSessao()
        - void visualizarSessoes()
    }

    class MenuFilme {
        - FilmeService filmeService
        - Funcionario funcionarioLogado
        - Scanner scanner

        + MenuFilme(Funcionario, Scanner, FilmeService)
        + MenuFilme(Cliente, Scanner, FilmeService)
        + void exibirMenu()
        + void mostrarFilmesEmCartaz()

        # void tratarOpcao(int)
        - void cadastrarFilme()
        - void atualizarFilme()
        - void deletarFilmes()
    }
end

MenuFuncionario --|> MenuBase
MenuFilme --|> MenuBase

Main --> PainelControle
PainelControle ..> MenuCliente

MenuFilme --> FilmeService
MenuFilme --> Funcionario
MenuFilme --> Cliente
MenuFilme --> Scanner
MenuFilme --> Filme
MenuFilme --> GeneroFilme