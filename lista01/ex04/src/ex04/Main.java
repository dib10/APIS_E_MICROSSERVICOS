package ex04;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
	    System.out.println("Digite o nome do produto: ");
		String nomeProduto = sc.nextLine();
		
	    System.out.println("Digite o preço do produto: ");
		double precoUnitario = sc.nextDouble();
		
		System.out.println("Digite a quantidade do produto: ");
		int quantidade = sc.nextInt();
		
		Produto produto = new Produto(nomeProduto, precoUnitario,quantidade);
		
		double valorTotalComDesconto = produto.calcularDesconto();
        System.out.printf("Valor total : R$ %.2f\n", valorTotalComDesconto);
        sc.close();
	}

}
