package l2ex17;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite os segundos(s)");
		double segundos = sc.nextDouble();
		if (ConverteremHoras.verificaNegativo(segundos)) {
			System.out.println("Erro: número negativo");
			return;
			
		}
		double horas = ConverteremHoras.transformarEmHoras(segundos);
		System.out.println(horas + "horas");

	}

}
