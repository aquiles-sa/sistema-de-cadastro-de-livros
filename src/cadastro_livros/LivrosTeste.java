package cadastro_livros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import funcionalidades.PrecoUtils;
import funcionalidades.ListaUtils;
import funcionalidades.EdicaoUtils;

public class LivrosTeste {

	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {	
		Connection conn = FabricaConexao.getConexao();
		
		try {
			mostrarMenu(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void cadastrarLivro(Connection conn) throws SQLException {
		try {
			System.out.print("Digite o título do livro a ser inserido: ");
			String titulo = scan.nextLine();
			
			System.out.print("Digite o nome do(a) autor(a): ");
			String autor = scan.nextLine();
			
			System.out.print("Digite o ano de publicação: ");
			int ano = scan.nextInt();
			scan.nextLine();
			
			System.out.print("Digite o preço do livro: ");
			String precoString = scan.nextLine();
			PrecoUtils.tratarPreco(precoString);
			
			double preco;
			try {
				preco = PrecoUtils.tratarPreco(precoString); 
				if (preco < 0) throw new ErroNumeroNegativo();
			} catch (NumberFormatException e) {
				System.out.println("Error: " + e);
				return;
			}
			
			if (ano < 0) {
				throw new ErroNumeroNegativo();
			}
			
			Livro livro = new Livro(titulo, autor, ano, preco);
			
			String createSQL = "INSERT INTO livro (titulo, autor, ano_publicacao, preco) VALUES (?, ?, ?, ?)";
			
			try (PreparedStatement stmt = conn.prepareStatement(createSQL)){
				stmt.setString(1, livro.getTitulo());
				stmt.setString(2, livro.getNomeAutor());
				stmt.setInt(3, livro.getAnoPublicacao());
				stmt.setDouble(4, livro.getPreco());
				
				stmt.execute();
				System.out.println("Livro cadastrado com sucesso!\n");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InputMismatchException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		} catch (ErroNumeroNegativo e) {
		    System.out.println("Error: Ano de publicação e preço devem ser positivos.");
		}
	}
	
	static void listarLivros(Connection conn) throws SQLException {
		String sql = "SELECT * FROM livro";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			ResultSet resultado = stmt.executeQuery(sql);
			List<Livro> livros = new ArrayList<Livro>();
			
			while (resultado.next()) {
				int id = resultado.getInt("id");
				String titulo = resultado.getString("titulo");
				String autor = resultado.getString("autor");
				int anoPublicacao = resultado.getInt("ano_publicacao");
				double preco = resultado.getDouble("preco");
				
				Livro livro = new Livro(id, titulo, autor, anoPublicacao, preco);
				livros.add(livro);
			}
			
			ListaUtils.verificarListaVazia(livros);
			System.out.println();
		}
	}

	static void editarLivro(Connection conn) throws SQLException {
		System.out.print("Digite o id do livro a ser editado: ");
		int idLivroEditado = scan.nextInt();
		scan.nextLine();
		
		String selectSQL = "SELECT * FROM livro WHERE id = ?";
		
		try (PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
			selectStmt.setInt(1, idLivroEditado);
			ResultSet resultado = selectStmt.executeQuery();
			
			if (resultado.next()) {
				Livro livro = new Livro(
						resultado.getInt("id"), 
						resultado.getString("titulo"), 
						resultado.getString("autor"), 
						resultado.getInt("ano_publicacao"), 
						resultado.getDouble("preco"));
				
				System.out.println(livro + "\n");
				
				HashMap<Integer, String> campos = new HashMap<Integer, String>();
				campos.put(1, "Título");
				campos.put(2, "Autor");
				campos.put(3, "Ano de publicação");
				campos.put(4, "Preço");
				
				System.out.println("Digite qual informação será editada: ");
				campos.forEach((id, dado) -> System.out.println(id + " -> " + dado));
				
				int escolha = scan.nextInt();
				scan.nextLine();
				
				EdicaoUtils.editarInformacoes(escolha, idLivroEditado, conn);
			}
		}
	}
	
	static void excluirLivro(Connection conn) throws SQLException {
		System.out.print("Digite o id do livro a ser excluído: ");
		int idLivroExcluido = scan.nextInt();
		scan.nextLine();
		
		String selectSql = "SELECT * FROM livro WHERE id = ?";
		
		try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
			selectStmt.setInt(1, idLivroExcluido);
			ResultSet resultado = selectStmt.executeQuery();
			
			if (resultado.next()) {
				int id = resultado.getInt("id");
				String titulo = resultado.getString("titulo");
				String autor = resultado.getString("autor");
				int anoPublicacao = resultado.getInt("ano_publicacao");
				double preco = resultado.getDouble("preco");
				
				String opcao;
				
				Livro livro = new Livro(id, titulo, autor, anoPublicacao, preco);
				System.out.println("Livro a ser excluído: " + livro.getTitulo());
				System.out.print("Desejas excluir [S/N]: ");
				opcao = scan.nextLine();
				
				if ("s".equalsIgnoreCase(opcao)) {
					String deleteSql = "DELETE FROM livro WHERE id = ?";
					try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
						deleteStmt.setInt(1, idLivroExcluido);	
						int registrosAfetados = deleteStmt.executeUpdate();
						
						if (registrosAfetados > 0) {
							System.out.println("Livro excluído com sucesso!\n");
						} else {
							System.out.println("Erro ao excluir o livro.\n");
						}
					}
					
				} else if ("n".equalsIgnoreCase(opcao)) {
					System.out.println("Nada feito!\n");
				}
				
			} else {
				System.out.println("Livro com id '" + idLivroExcluido + "' não encontrado!\n");
			}
		}
	}
	
	static void procurarLivrosPorAutor(Connection conn) throws SQLException {
		System.out.print("Digite o nome do autor para visualizar seus livros: ");
		String nomeAutor = scan.nextLine();
		
		String selectSql = "SELECT * FROM livro WHERE autor LIKE ?";
		try (PreparedStatement stmt = conn.prepareStatement(selectSql)) {
			stmt.setString(1, "%" + nomeAutor.toLowerCase() + "%");
			ResultSet resultado = stmt.executeQuery();
			
			List<Livro> livros = new ArrayList<Livro>();
			
			while (resultado.next()) {
				int id = resultado.getInt("id");
				String titulo = resultado.getString("titulo");
				String autor = resultado.getString("autor");
				int anoPublicacao = resultado.getInt("ano_publicacao");
				double preco = resultado.getDouble("preco");
				
				Livro livro = new Livro(id, titulo, autor, anoPublicacao, preco);
				livros.add(livro);
			}
			
			ListaUtils.verificarListaVazia(livros, nomeAutor);
			System.out.println();
		}
	}
	
	static void mostrarMenu(Connection conn) throws SQLException {
		int escolha = 0;
		
		do {
			System.out.println("##### MENU #####");
			System.out.println("1 - Cadastrar novo livro.");
			System.out.println("2 - Visualizar livros.");
			System.out.println("3 - Excluir livro.");
			System.out.println("4 - Procurar livros por autor.");
			System.out.println("5 - Editar livro.");
			System.out.println("6 - Sair do programa.");
			System.out.println("################");
			System.out.print("Opção: ");
			
			escolha = scan.nextInt();
			scan.nextLine();
			
			switch (escolha) {
				case 1: {
					cadastrarLivro(conn);
					break;
				}
				case 2: {
					listarLivros(conn);
					break;
				}
				case 3: {
					excluirLivro(conn);
					break;
				}
				case 4: {
					procurarLivrosPorAutor(conn);
					break;
				}
				case 5: {
					editarLivro(conn);
					break;
				}
				case 6: {
					System.out.println("Programa encerrado.");
					break;
				}
				default: {
					throw new IllegalArgumentException("Valor inesperado: " + escolha);
				}
			}
		} while (escolha != 6);
		
	}
}
