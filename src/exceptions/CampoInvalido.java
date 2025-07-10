package exceptions;

public class CampoInvalido extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CampoInvalido(String mensagem) {
        super(mensagem);
    }

    public CampoInvalido(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    
}
