package l2ex13;

public class Triangulo {
	public static boolean ehTriangulo(double a, double b, double c) {
		
		if (a <=0 || b<=0 || c<=0) {
			return false;
			
		}
		
		return(a+b>c) && (a+c >b) && (b+c >a);
		
		
		
	}
	
	public static String tipoTriangulo(double a, double b, double c) {
		if(a==b && b ==c)
		{
			return "equilatero";
		}
		else if (a==b || a==c || b==c) {
			return "isosceles";
		}
		else {
			return "escaleno";
		}
	}
	
	

}
