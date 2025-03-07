package l2ex05;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite um número");
		
		double num = sc.nextDouble();
		
		if (num % 2 == 0 ) {
			
			System.out.println("Par");
			
		} else {
			
			System.out.println("ímpar");

		}

	}

}
