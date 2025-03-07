package l2ex09;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int [] numeros = new int[3];
		Scanner sc = new Scanner(System.in);
		
		
		for (int i = 0; i<3;i++) {
			System.out.println("Digite o " + (i+1)+ "numero");
			numeros[i] = sc.nextInt();
		}
		int menor = numeros[0];

		
		for (int i = 1; i < numeros.length; i++) {
			if(numeros[i]<menor) {
				menor = numeros[i];
			}
			
			
		}
		System.out.println("O menor número é: " + menor);

		
		
	}

}
