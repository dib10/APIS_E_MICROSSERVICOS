package ex05;

public class Tabuada {
	
	public void gerarTabuada() {
		for(int i =1; i <=10; i++) {
			System.out.println("Tabuada do " + i + ":");
			for(int j = 1; j<=10; j++) {
				System.out.println(i +"x" + j + "=" + (i*j));
			}
			System.out.println("");
		}
	}

}
