package ex04;

public class Produto {
	String nome;
	double preco;
	int quantidade;
	
	public Produto(String nome, double preco, int quantidade)
	{
		this.nome = nome;
		this.preco = preco;
		this.quantidade = quantidade;
	}
	
	public double calcularDesconto() {
		
		double desconto = 0.0;
	
		if(quantidade >= 11 && quantidade <=20) {
			desconto =  0.10;
		}
		else if (quantidade >= 21 && quantidade <=50) {
			desconto = 0.20;
		}
		else if (quantidade >50) {
			desconto = 0.25;
		}
		return preco * quantidade * (1-desconto);
	

	}
}



	
	
