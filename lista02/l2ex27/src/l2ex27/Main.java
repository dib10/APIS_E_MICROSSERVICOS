package l2ex27;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		CalculadoraMedia calculadora = new CalculadoraMedia();
		
		System.out.println("Digite as notas: ");
		while (true) {
			double nota = sc.nextDouble();
			
			if (nota<0) {
				break;
			}
			calculadora.adicionarNota(nota);
		}
		
		if (calculadora.temNotasValidas()) {
			System.out.println("Média: " + calculadora.calcularMedia());
		} else {
			System.out.println("Nenhuma nota válida inserida");
		}
		
		
		

	}
	
	

}
