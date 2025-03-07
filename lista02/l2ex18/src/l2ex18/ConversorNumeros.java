package l2ex18;

public class ConversorNumeros {
	
	public static final String[] UNIDADES = {
 "zero", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove",
    "dez", "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezessete", 
    "dezoito", "dezenove"
	};
	
	private static final String[] DEZENAS = {
    "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa"

			
	};
	
	//converter
	public static String converterParaExtenso(int numero) {
		if (numero<0 || numero>100) {
			return "Erro, número fora do invervalo";
		}
		if (numero == 100) {
			return "cem";
		}
		if (numero <20) {
			return UNIDADES[numero];
		}
		
		int dezena = numero/10;
		int unidade = numero % 10;
		
		String extenso = DEZENAS[dezena -2]; //p ajustar indice
		
		if (unidade == 0) {
			return extenso;
			
		}
		else {
			return extenso + " " + "e" + " " + UNIDADES[unidade];
		}
	}

}
