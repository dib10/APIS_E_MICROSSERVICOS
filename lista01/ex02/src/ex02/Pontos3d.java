package ex02;

public class Pontos3d {
    private double x, y, z;

	
	public Pontos3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	//fórmula distância
	
	public double calcularDistancia(Pontos3d outroponto) {
		double dx = outroponto.x = this.x;
		double dy = outroponto.y = this.y;
		double dz = outroponto.z = this.z;
		return Math.sqrt(dx * dx +dy *dy +dz *dz);
		
	}

}
