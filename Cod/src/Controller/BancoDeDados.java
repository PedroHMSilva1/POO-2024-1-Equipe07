package Controller;

import java.awt.HeadlessException;
import java.sql.*;
import javax.swing.JOptionPane;

public class BancoDeDados {
	static ConexaoMySQL conexao = new ConexaoMySQL();
	static Connection cn = null;
	static Statement st = null;
	static PreparedStatement ps = null; 
	public static void main(String[] args) {		 
		try {
			String operacao[] = {"Cadastro","Impressão", "Consultar", "Atualização","Exclusão"};
			Object escolha = JOptionPane.showInputDialog(null, "Qual operação deseja realizar?",
					"Cadastro Fornecedor", JOptionPane.QUESTION_MESSAGE, null, operacao, operacao[0]);
			realizaOperacao(escolha);
		} catch (Exception e) {
			e.printStackTrace();
		}    
	}
	private static void realizaOperacao(Object escolha) throws Exception {		      
		try {
			cn = conexao.openDB();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		st = cn.createStatement();
		if(escolha.toString()=="Cadastro")
			cadastrarAluno();
		else if (escolha.toString()=="Consultar")
			consultarAlunos();
		else if (escolha.toString()=="Impressão")
			imprimirAlunos();
		else if (escolha.toString()=="Atualização")
			atualizarAlunos();
		else if (escolha.toString()=="Exclusão")
			excluirAlunos();
	}


	private static void imprimirAlunos() throws SQLException {
		ResultSet rs = st.executeQuery("SELECT * FROM pi.login");
		while (rs.next()) {
			int  id  = rs.getInt("idalunos");
			String nome = rs.getString("alunosNome");
			String telefone = rs.getString("alunosTelefone");
			double nota = rs.getDouble("alunosNota");
			System.out.println("\nID: " + id + "\tNome: " + nome 
					+ " Telefone: " + telefone + "\tNota: " + nota);
		}            
	}
	private static void excluirAlunos() throws HeadlessException, Exception {
		//EXCLUINDO UM REGISTRO 
		String nome = JOptionPane.showInputDialog("Digite o nome do aluno a ser EXCLUÍDO");
		Aluno consultado = consultarAlunos(nome);
		int resp = JOptionPane.showConfirmDialog(null, "Confirma a exclusão do aluno: " 
				+ consultado);
		if (resp==0) {			
			st.executeUpdate("DELETE FROM alunos WHERE alunosNome = '" + nome +"' ");
			imprimirAlunos();
			cn.close();
			ps.close();	
		}
		else
			JOptionPane.showMessageDialog(null, "O aluno: " + nome + " NÃO foi excluído");  	
	}
	private static Aluno consultarAlunos(String nome) throws Exception {
		Aluno lido = null;
		try {
			String queryCmd = "select * from alunos where "
					+ "alunosNome like ? ";
			ps = cn.prepareStatement(queryCmd);
			ps.setString(1, nome); 			
			ResultSet view = ps.executeQuery();			
			while(view.next()) {			
				int id = view.getInt("idalunos");
				String nomeLido = view.getString("alunosNome");
				String telefone = view.getString("alunosTelefone");
				double nota = view.getDouble("alunosNota");
				lido = new Aluno(id, nomeLido, telefone, nota);		
			}
		} catch (SQLException e) {
			throw new Exception(e); // encapsula excecao original
		} finally {

		}
		return lido;
	}
	private static void atualizarAlunos() throws HeadlessException, Exception {
		String nome = JOptionPane.showInputDialog("Digite o nome do aluno a alterar");
		Aluno consultado = consultarAlunos(nome);
		int resp = JOptionPane.showConfirmDialog(null, "Confirma a alteração do aluno: " 
				+ consultado);
		PreparedStatement ps = cn.prepareStatement(
				"UPDATE Alunos SET alunosNome = ?, alunosTelefone=?, alunosNota=? "
						+ "where alunosNome =  '" + nome +"' ");
		ps.setString(1, JOptionPane.showInputDialog("Digite o nome"));
		ps.setString(2, JOptionPane.showInputDialog("Digite o Telefone"));
		ps.setDouble(3, Double.parseDouble(JOptionPane.showInputDialog("Digite a nota")));                
		ps.execute();   
		cn.close();
		ps.close();	
	}
	private static void consultarAlunos() throws Exception {
		try {
			String queryCmd = "select * from alunos where "
					+ "alunosNome like ? ";
			Aluno aluno =null;

			ps = cn.prepareStatement(queryCmd);
			ps.setString(1, (JOptionPane.showInputDialog("Digite o nome do aluno:"))); 			
			ResultSet view = ps.executeQuery();			
			while(view.next()) {			
				int id = view.getInt("idalunos");
				String nome = view.getString("alunosNome");
				String telefone = view.getString("alunosTelefone");
				double nota = view.getDouble("alunosNota");
				JOptionPane.showMessageDialog(null, "Dados do Aluno:\n " + "\nID: " + id + 
						"\nNome: " + nome +"\nTelefone: " + telefone + "\nNota: " + nota);		
			}
		} catch (SQLException e) {
			throw new Exception(e); // encapsula excecao original
		} finally {		
		}			 	
	}
	private static void cadastrarAluno() throws SQLException {
		String nome = JOptionPane.showInputDialog("Digite o nome");
		String telefone = JOptionPane.showInputDialog("Digite o Telefone");
		double nota = Double.parseDouble(JOptionPane.showInputDialog("Digite a Nota"));
		PreparedStatement ps = cn.prepareStatement(
				"INSERT INTO Alunos (alunosNome, alunosTelefone, alunosNota) "
						+ "VALUES (?, ?, ?)");
		ps.setString(1, nome);
		ps.setString(2, telefone);
		ps.setDouble(3, nota);                
		ps.executeUpdate();   
		imprimirAlunos();
		cn.close();
		ps.close();	
	}	
}

