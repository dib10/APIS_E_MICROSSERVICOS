package ex08;

public class Fibonacci {
	
	//uso de array
	
	public static int [] calcularFibonacci(int n) {
		if(n<0) {
			System.out.println("Número negativo");
			return new int[0];
		}
		int [] fib = new int [n +1]; // pq temos 0 
		fib[0] = 0; // começa com 0 -> F0
		if (n>0) {
			fib[1] = 1; // segundo elemento - > f1
		}
		
		//a partir do 3 elemento
		for(int i = 2; i <=n; i++) {
			//Fn = Fn-1 + Fn-2
			fib[i] = fib[i-1] + fib[i-2];
		}
		return fib; 
	}
	
	//exibir
	
	public static void exibirFibonacci(int [] serieFibonacci) {
		System.out.println("Série até:" + (serieFibonacci.length -1)+ ":");
		for (int i = 0; i < serieFibonacci.length; i++) {
            System.out.print(serieFibonacci[i]);
            if (i < serieFibonacci.length - 1) {
                System.out.print(", "); // Adiciona vírgula entre os números
            }
        }
	}
	

}
