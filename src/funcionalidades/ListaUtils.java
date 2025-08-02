package funcionalidades;

import java.util.List;

public interface ListaUtils {
	public static <T> void verificarListaVazia(List<T> lista) {
		if (lista.isEmpty()) {
			System.out.println("Não há livros registrados.\n");
		} else {
			lista.stream().forEach(System.out::println);
		}
	}
	
	public static <T> void verificarListaVazia(List<T> lista, String nomeAutor) {
		if (lista.isEmpty()) {
			System.out.println("Nenhum livro foi encontrado pelo autor '" + nomeAutor + "'");
		} else {
			lista.stream().forEach(System.out::println);
		}
	}
}
