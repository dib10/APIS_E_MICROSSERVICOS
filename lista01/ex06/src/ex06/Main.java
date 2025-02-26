package ex06;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite um número: ");
		int numero = sc.nextInt();
		int quantidadeDigitos = Digitos.contarDigitos(numero);
		
		System.out.println("O número " + numero + " tem " + quantidadeDigitos + " digitos");
		

	}

}
