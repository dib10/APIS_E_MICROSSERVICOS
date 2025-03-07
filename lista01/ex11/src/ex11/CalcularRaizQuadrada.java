package ex11;
//método babilonico p esse ex
public class CalcularRaizQuadrada {
	
	public static double calcularRaiz(int numero, double erroMax) {
		
		if(numero < 0 ) { //núm negativo
			throw new IllegalArgumentException("O número não pode ser negativo");
			
		}
		if(numero == 0) {
			return 0.0;
		}
		
		double palpite = numero / 2.0; //
		double erro = calcularErro(palpite, numero);
		
		while (erro > erroMax) {
			palpite = (palpite + numero / palpite) / 2.0;
			erro = calcularErro(palpite, numero);
			System.out.println(palpite);//debug
		}
		return palpite;
		
	}
	private static double calcularErro(double palpite, int numero) {
		double quadrado = palpite * palpite;
		double diferenca = quadrado - numero;
		if(diferenca < 0) {
			return -diferenca;
		}
		else {
			return diferenca;
		}
	}

}
