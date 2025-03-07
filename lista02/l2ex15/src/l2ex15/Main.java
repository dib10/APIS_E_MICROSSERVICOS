package l2ex15;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite um número para X: ");
		int x = sc.nextInt();
		
		System.out.println("Digite um número para y");
		int y = sc.nextInt();
		
		int multiplicar = Operacoes.multiplicar(x, y);
		int somar = Operacoes.somar(x, y);
		String comparar = Operacoes.comparar(x, y);
		
		System.out.printf("Soma entre %d e %d = %d",x,y,somar );
		System.out.println("");
		System.out.printf("Produto entre %d e %d = %d",x,y,multiplicar );
		System.out.println("");
		System.out.println("Comparação: " + comparar);
		


	}

}
