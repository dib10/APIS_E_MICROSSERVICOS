package l2ex24;

import java.util.Scanner;

public class TabuadaWhile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		int numero = sc.nextInt();
		
		if (numero < 1 || numero > 20) { //entre 1 e 20
			System.out.println("Número invalido");
			return;
		}
		System.out.println("Tabuada do: " + numero);
		int i = 1;
		while (i <=10) {
			System.out.printf("%d x %d =%d%n", numero,i, numero*i);
			i++;
		}
		

	}

}
