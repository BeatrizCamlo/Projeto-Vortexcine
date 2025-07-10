package interfaces;

public interface RepositorioInterface<T> {

    void cadastrar(T entidade);

    void remover(T entidade);

    void editarNome(T entidade, String novoNome);

    void editarEmail(T entidade, String novoEmail);

    void editarSenha(T entidade, String novaSenha);

    T obterPorNome(String nome);

    T obterPorId(int id);

    T obterPorEmail(String email);


}
