package ex06;

public class Digitos {

	public static int contarDigitos(int numero) {
		if(numero == 0) return 1; //se for 0, só tem 1 digito
		
		int contador = 0;
		numero = Math.abs(numero); // converte negativo
		
		while(numero > 0) {
			numero = numero / 10;
			contador ++;
		}
		return contador;
	}
	
}

// isso funciona pois foi declarado como int, ou seja
// ta pegando só a parte inteira. Ex: 56/10 = 5.6 => 5

