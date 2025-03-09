package l2ex25;

import java.util.Scanner;

public class SomaNumero {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite a quantidade de números: ");
		int n = sc.nextInt();
		
		if (n<=0) {
			System.out.println("Erro");
			sc.close();
			return;
		}
		
		double soma = 0.0;
		
		System.out.println("Digite os " + n + "Números");
		//captar os numeros do usu
		for (int i = 1; i <=n; i++) {
			System.out.println("Número " + i + ":");
			double numero = sc.nextDouble();
			soma+=numero;
		}
        System.out.printf("\nSOMA TOTAL: %.2f", soma);

	}

}
