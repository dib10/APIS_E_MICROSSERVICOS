package l2ex12;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite a quantidade de copos comprados");
		int quantidade = sc.nextInt();
		
		double precoTotal = CalculadoraPrecoCopos.calcularPrecoTotal(quantidade);
		System.out.println("Preço total: " + precoTotal);
		
		sc.close();
	}

}
