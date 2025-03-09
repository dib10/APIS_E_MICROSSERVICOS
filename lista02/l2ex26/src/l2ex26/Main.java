package l2ex26;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		CalcularSoma calculadora = new CalcularSoma();
		
		
		System.out.println("Digite os números para somar");
		
		int numero;
		
		do {
			numero = sc.nextInt();
			if (numero!=0) {
				calculadora.adicionarNumero(numero);
			}
		} while(numero!=0);
		
		System.out.println("A soma é: " + calculadora.getSoma());
	}

}
