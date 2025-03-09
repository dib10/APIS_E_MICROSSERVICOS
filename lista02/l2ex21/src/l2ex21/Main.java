package l2ex21;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		System.out.println("a: ");
		double a = sc.nextDouble();
		
		System.out.println("b: ");
		double b = sc.nextDouble();
		
		System.out.println("c: ");
		double c = sc.nextDouble();
		
		
		if (a == 0) {
			System.out.println("A não pode ser um número negativo");
			return;

		}

	
	
	double delta = Math.pow(b, 2) - 4 *a*c;
	System.out.println("Delta: " + delta);
	
	if (delta > 0) {
        double x1 = (-b + Math.sqrt(delta)) / (2 * a);
        double x2 = (-b - Math.sqrt(delta)) / (2 * a);
        System.out.println("Raízes reais e distintas:");
        System.out.printf("x1 = %.2f%n", x1);
        System.out.printf("x2 = %.2f%n", x2);
    } else if (delta == 0) {
        double x = -b / (2 * a);
        System.out.println("Raiz real única (dupla):");
        System.out.printf("x = %.2f%n", x);
    } else {
        double parteReal = -b / (2 * a);
        double parteImaginaria = Math.sqrt(-delta) / (2 * a);
        System.out.println("Raízes complexas:");
        System.out.printf("x1 = %.2f + %.2fi%n", parteReal, parteImaginaria);
        System.out.printf("x2 = %.2f - %.2fi%n", parteReal, parteImaginaria);
    }
	
	sc.close();
	

}
}
