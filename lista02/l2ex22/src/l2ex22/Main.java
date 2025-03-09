package l2ex22;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.println("Abaixo, digite os valores solicitados");
		
		System.out.println("Posição inicial(P0)");
		
		double p0 = sc.nextDouble();
		
		System.out.println("Velocidade Inicial(V): ");
		double v = sc.nextDouble();
		
		System.out.println("Aceleração (A): ");
		double a = sc.nextDouble();
		
		System.out.println("Tempo: ");
		int t = sc.nextInt();
		
		
		//		formula
		
		double pf = p0 + (v*t) + (a*t *t) / 2;
		System.out.println("\nPosição final: " + "" + pf);
		
		
	}

}
