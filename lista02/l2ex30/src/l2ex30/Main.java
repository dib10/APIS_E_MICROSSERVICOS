package l2ex30;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite o coeficiente a");
		double a = sc.nextDouble();
		
		System.out.println("Digite o coeficiente b: ");
		
		double b = sc.nextDouble();
		
		System.out.println("Digite o limite inferior de x");
		double xInicial = sc.nextDouble();

		System.out.println("Digite o limite superior de X ");
		double xFinal = sc.nextDouble();
		
		System.out.println("Digite o incrementod e X: ");
		double incremento = sc.nextDouble();
		
		if (incremento <= 0) {
			System.out.println("O incremento deve ser positivo");
			sc.close();
			return;
		}
		
		FuncaoLinear funcao = new FuncaoLinear(a, b);
		System.out.println("\nTabela de Valores:");
        System.out.println("-----------------");
        System.out.println("   x    |    y   ");
        System.out.println("-----------------");
        
        
        for (double x = xInicial; x <= xFinal; x += incremento) {
            double y = funcao.calcularY(x);
            System.out.printf("%6.2f  | %6.2f%n", x, y); 
        }
		
	}

}
