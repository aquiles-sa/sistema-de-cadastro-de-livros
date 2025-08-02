package funcionalidades;

public interface PrecoUtils {
	public static double tratarPreco(String preco) throws NumberFormatException {
		String preco_string = preco.replace(",", ".");
		return Double.parseDouble(preco_string);
	}
}
