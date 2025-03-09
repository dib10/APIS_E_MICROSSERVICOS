package l2ex27;

public class CalculadoraMedia {
	
	private double soma;
	private int contador;
	
	public CalculadoraMedia() {
		soma = 0.0;
		contador = 0;
	}
	
	public void adicionarNota(double nota) {
		soma += nota;
		contador++;
	}
	
	public double calcularMedia() {
		return soma/contador;
	}

		public boolean temNotasValidas() {
			return contador > 0;
		}

}
