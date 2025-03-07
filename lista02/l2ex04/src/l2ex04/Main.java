package l2ex04;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite um número");
		
		double num = sc.nextDouble();
		
		if (num > 100) {
			System.out.println("Valor maior que 100");
			
		} else {
			System.out.println("Valor menor ou igual a 100");

		}
		
		

	}

}
