package exceptions;

public class ErroDeSeguranca extends RuntimeException {
    
    public ErroDeSeguranca(String mensagem) {
        super(mensagem);
    }
    
    public ErroDeSeguranca(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    
    public ErroDeSeguranca(Throwable causa) {
        super(causa);
    }
}
