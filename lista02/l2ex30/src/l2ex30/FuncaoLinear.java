package l2ex30;

public class FuncaoLinear {
	
	private double a;
	private double b;
	
	public FuncaoLinear(double a, double b) {
		this.a = a;
		this.b = b;
	}
	
	public double calcularY(double x) {
		return a * x +b;
	}

}
