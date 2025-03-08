package l2ex18;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub+ " "
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite um numero entre 0  e 100");
		int numero = sc.nextInt();
		
		String numeroExtenso = ConversorNumeros.converterParaExtenso(numero);
        System.out.println(numeroExtenso);
	}

}
