package l2ex14;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite a primeira nota: ");
		double nota1 = sc.nextDouble();
		
		System.out.println("Digite a segunda nota");
		double nota2 = sc.nextDouble();
		
		System.out.println("Digite a terceira nota: ");
		double nota3 = sc.nextDouble();
		
		double media = MediaFinal.calcularMedia(nota1, nota2, nota3);
		
		//imprimir resultado
		
		String resultado = MediaFinal.Status(media);
		
		System.out.println("Média" + media + "," + resultado);
		

	}

}
