package l2ex11;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite o número A(diferente de 0): ");
		int A = sc.nextInt();
		
		System.out.println("Digite o número B (diferente de 0): ");
		int B = sc.nextInt();
		
		if (A % B == 0) {
			System.out.println(A + " é múltiplo de " + B);
			
		} 
		
		if(B%A == 0) {
			System.out.println(B + " é múltiplo de " + A);
		}
		

	}

}
