package l2ex20;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite a medida em metros");
		double metros = sc.nextDouble();
		
		if (metros < 0) {
			System.out.println("Valor negativo. Não é possível realizar a conversão");
		}
		else {
			double pes = ConversorPes.converterParaPes(metros);
			
			System.out.println("\n Valor em metros: " + metros);
			System.out.println("Valor convertido para pés " + pes);
		}
		sc.close();
	}

}
