package l2ex15;

public class Operacoes {
	
	public static int somar(int x, int y) {
		return x+y;

	}
	
	public static int multiplicar(int x, int y) {
		return x*y;
	}
	
	public static String comparar(int x, int y) {
		if (x>y) {
			
			return "x>y";
			
		}
		else if (x<y) {
			return "x<y";
			
		}
		else {
			return "x=y";
		}
	}

}
