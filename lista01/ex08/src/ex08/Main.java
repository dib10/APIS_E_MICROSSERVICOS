package ex08;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Fibonacci");
		System.out.println("Informe um número inteiro positivo");
		int n = sc.nextInt();
		
		int[] fibonacciSerie = Fibonacci.calcularFibonacci(n);

        Fibonacci.exibirFibonacci(fibonacciSerie);
		
		
		

	}

}
