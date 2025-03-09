package l2ex24;

import java.util.Scanner;

public class DoWhile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite um número entre 1 e 20; ");
		int numero = sc.nextInt();
		
		if (numero < 1 || numero > 20) {
			System.out.println("Número inválido");
			return;
		}
		
		System.out.println("Tabuada do: " + numero);
		
		int i = 1;
		
		do {
			System.out.printf("%d x %d = %d%n", numero,i,numero*i);
			i++;
		} while(i<=10);

	}

}
