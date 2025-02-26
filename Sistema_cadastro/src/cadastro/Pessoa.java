package cadastro;

public abstract class Pessoa {
	String nome;
	Data nascimento; // objeto da classe Data
	
	
	//construtor
	
	public Pessoa(String nome, Data nascimento) {
		this.nome = nome;
		this.nascimento = nascimento;
	}
	
	public abstract void imprimeDados();

}
