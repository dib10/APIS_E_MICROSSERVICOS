package l2ex06;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o valor de A");
		int A = sc.nextInt();
		
		System.out.println("Digite o valor de B");
		int B = sc.nextInt();
		
		System.out.println("Digite o valor de C");
		int C = sc.nextInt();
		
		String respostaQuestaoA;
		String respostaQuestaoB;
		String respostaQuestaoC;
		
		if (A == 0 ) {
			respostaQuestaoA = "V";
			
		} else {
			respostaQuestaoA = "F";
		}
		
		if (B<0 ) {
			respostaQuestaoB = "V";
			
			
		} else {
			respostaQuestaoB = "F";

		}
		
		if (A>0 || B < 0) {
			respostaQuestaoC = "V";
			
		} else {
			respostaQuestaoC = "F";

		}
		
		
		System.out.println("(a) A é igual a zero?: " + respostaQuestaoA);
		System.out.println("(b) B é menor que zero? " + respostaQuestaoB);
        System.out.println("(c) A OU B são maiores do que zero? " + respostaQuestaoC);
	   
        sc.close();

		
		

	}

}
