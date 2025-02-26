package ex01;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite o valor de a: ");
		double a = sc.nextDouble();
		
		System.out.println("Digite o valor de b: ");
		double b = sc.nextDouble();
		
		System.out.println("Digite o valor de c: ");
		double c = sc.nextDouble();
		
		if(a == 0 ) {
			System.out.println("Valor de 'a' não pode ser zero.");
		}
		else {
			EquacaoSegundoGrau equacao = new EquacaoSegundoGrau(a,b,c);
			equacao.calcularRaizes();
		}
		sc.close();
		
		

	}

}
