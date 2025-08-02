package funcionalidades;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;

public interface EdicaoUtils {
	Scanner scan = new Scanner(System.in);
	
	public static void editarInformacoes(int opcao, int idLivroEditado, Connection conn) throws SQLException {
		if (opcao == 1) {
			System.out.print("Digite o novo título: ");
			String novoTitulo = scan.nextLine();
			
			String updateSql = "UPDATE livro SET titulo = ? WHERE id = ?";
			
			try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
				stmt.setString(1, novoTitulo);
				stmt.setInt(2, idLivroEditado);
				stmt.executeUpdate();
				System.out.println("Título atualizado com sucesso!\n");
			}
		}
		
		if (opcao == 2) {
			System.out.print("Digite o novo nome do autor: ");
			String novoNomeAutor = scan.nextLine();
			
			String updateSql = "UPDATE livro SET autor = ? WHERE id = ?";
			
			try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
				stmt.setString(1, novoNomeAutor);
				stmt.setInt(2, idLivroEditado);
				stmt.executeUpdate();
				System.out.println("Autor atualizado com sucesso!\n");
			}
		}
		
		if (opcao == 3) {
			System.out.print("Digite o novo ano de publicação: ");
			int novoAnoPublicacao = scan.nextInt(); 
			scan.nextLine();
			
			String updateSql = "UPDATE livro SET ano_publicacao = ? WHERE id = ?";
			
			try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
				stmt.setInt(1, novoAnoPublicacao);
				stmt.setInt(2, idLivroEditado);
				stmt.executeUpdate();
				System.out.println("Ano de publicação atualizado com sucesso!\n");
			}
		}
		
		if (opcao == 4) {
			System.out.print("Digite o novo preço: ");
			double novoPreco = scan.nextDouble(); 
			scan.nextLine();
			
			String updateSql = "UPDATE livro SET preco = ? WHERE id = ?";
			
			try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
				stmt.setDouble(1, novoPreco);
				stmt.setInt(2, idLivroEditado);
				stmt.executeUpdate();
				System.out.println("Preço atualizado com sucesso!\n");
			}
		}
	}
}