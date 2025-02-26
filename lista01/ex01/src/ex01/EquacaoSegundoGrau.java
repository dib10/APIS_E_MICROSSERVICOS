package ex01;

public class EquacaoSegundoGrau {
	
	private double a,b,c;
	
	public EquacaoSegundoGrau(double a,
			double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	
	}
	
	public double calcularDelta() {
		return (b * b) - (4* a * c);
	}
	
	public void calcularRaizes( ) {
		double delta = calcularDelta();
		
		if (delta < 0) {
			System.out.println("Equação sem raízes reais");
		}
		else if (delta == 0) {
			double raiz = -b /(2*a);
			System.out.println("A raíz da equação é: " + raiz);
		}
		else { // se for dif de 0 e não nula, teremos duas raízes
			double raiz1 = (-b + Math.sqrt(delta)) /(2*a);
			double raiz2 = (-b - Math.sqrt(delta)) /(2*a);
			System.out.println("As raízes da equação são: " + raiz1 + " e "  + raiz2);
		}
	}

}
