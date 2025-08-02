package cadastro_livros;

@SuppressWarnings("serial")
public class ErroNumeroNegativo extends Exception {
	public ErroNumeroNegativo() {
		super("Há números negativos.");
	}
	
	public ErroNumeroNegativo(String mensagem) {
		super(mensagem);
	}
}
