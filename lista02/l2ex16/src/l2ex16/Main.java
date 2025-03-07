package l2ex16;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Informe a base do triangulo: ");
		double base = sc.nextDouble();
		System.out.println("Informe a altura do triangulo");
		double altura = sc.nextDouble();
		
		double resultado = Areatriangulo.calcularArea(base, altura);
		System.out.println("área= " + resultado);
		
		
		

	}

}
