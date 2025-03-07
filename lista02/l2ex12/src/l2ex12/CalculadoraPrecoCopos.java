package l2ex12;

public class CalculadoraPrecoCopos {
	
	public static double calcularPrecoTotal(int quantidade) {
		double precoUnitario;
		
		if (quantidade <=100) {
			precoUnitario = 0.05;
			
		}
		else if (quantidade <= 500) {
			precoUnitario = 0.04;
		}
		else {
			precoUnitario = 0.035;
		}
		return quantidade * precoUnitario;
	}

}
