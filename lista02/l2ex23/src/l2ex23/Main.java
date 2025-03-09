package l2ex23;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Capital inicial: ");
		double pv = sc.nextDouble();
		
		System.out.println("Taxa de juro: ");
		double j  = sc.nextDouble();
		
		System.out.println("Periódos (N)");
		int n = sc.nextInt();
		
		//capital futuro
		
		double fv = CalculadoraFinanceira.calcularFV(pv, j, n);
		
		System.out.println("Capital futuro: " + fv);
		sc.close();
	}

}
