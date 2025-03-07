package ex11;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite um número inteiro - não negativo");
		int numero = sc.nextInt();
		
		System.out.println("Digite o erro máx permitido: ");
		double erroMax = sc.nextDouble();
		
		
		try {
			double raizAproximada = CalcularRaizQuadrada.calcularRaiz(numero, erroMax);
			System.out.printf("A raiz quadrada aproximada de %d é %.5f%n", numero, raizAproximada);
		} catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
		sc.close();
	}

}
