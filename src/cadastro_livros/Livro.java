package cadastro_livros;

public class Livro {
	private int codigo;
	private String titulo;
	private String nomeAutor;
	private int anoPublicacao;
	private double preco;

	public Livro(int id, String titulo, String nomeAutor, int anoPublicacao, double preco) {
		this.codigo = id;
		this.titulo = titulo;
		this.nomeAutor = nomeAutor;
		this.anoPublicacao = anoPublicacao;
		this.preco = preco;
	}
	
	public Livro(String titulo, String nomeAutor, int anoPublicacao, double preco) {
		this.titulo = titulo;
		this.nomeAutor = nomeAutor;
		this.anoPublicacao = anoPublicacao;
		this.preco = preco;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public int getAnoPublicacao() {
		return anoPublicacao;
	}

	public double getPreco() {
		return preco;
	}
	
	public String toString() {
		return "ID: " + this.codigo + " | " + "título: " + this.titulo + " | " + "autor: " + this.nomeAutor + " | " + "ano_publicacao: " + this.anoPublicacao + " | " + "preço: " + this.preco;
	}
}
