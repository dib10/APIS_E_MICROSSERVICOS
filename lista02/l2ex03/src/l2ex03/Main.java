package l2ex03;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Informe um número: ");
		double numero = sc.nextDouble();
		
		if (numero > 50) {
			System.out.println("O número é maior que 50");
		} else {
			System.out.println("O número é menor ou igual a 50");

		}

	}

}
