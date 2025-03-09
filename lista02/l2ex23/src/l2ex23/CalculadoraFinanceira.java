package l2ex23;

public class CalculadoraFinanceira {
    //* FV = PV * (1 + J)^N
	
	public static double calcularFV(double pv, double j, int n) {
		return pv * Math.pow(1 + j, n);
	}


}
