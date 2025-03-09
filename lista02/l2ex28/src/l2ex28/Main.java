package l2ex28;

import java.util.Iterator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("A | B | C | A E B E C");
		System.out.println("--------------------");
		
		
		// 0 = F, 1 = V;
		for (int a = 0 ; a <= 1; a++) {
			for (int b = 0 ; b <= 1; b++) {
				for (int c = 0 ; c <= 1; c++) {
					boolean boolA = (a ==1);
					boolean boolB = (b==1);
					boolean boolC = (c==1);
					
					String linha = TabelaVerdade.gerarLinha(boolA, boolB, boolC);
					System.out.println(linha);
				}

			}
		}
	}

}
