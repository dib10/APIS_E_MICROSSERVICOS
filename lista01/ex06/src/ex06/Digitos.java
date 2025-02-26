package ex06;

public class Digitos {

	public static int contarDigitos(int numero) {
		if(numero == 0) return 1;
		
		int contador = 0;
		numero = Math.abs(numero); // converte negativo
		
		while(numero > 0) {
			numero = numero / 10;
			contador ++;
		}
		return contador;
	}
	
}
