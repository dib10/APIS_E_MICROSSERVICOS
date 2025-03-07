package l2ex13;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite os lados do Triangulo; ");
		
		
		System.out.println("Lado A: ");
		double A = sc.nextDouble();
		
		
		System.out.println("Lado B: ");
		double B = sc.nextDouble();
		
		System.out.println("Lado C: ");
		double C = sc.nextDouble();
		
		if(Triangulo.ehTriangulo(A, B, C))
		{
			String tipo = Triangulo.ehTriangulo(A, B, C);
			System.out.println("Triângulo" + tipo);
		}
		else {
			System.out.println("Os valores informados não formam um triângulo válido");
		}
		
		sc.close();
		
		

	}

}
