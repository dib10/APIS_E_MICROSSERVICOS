package l2ex10;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);

		int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();
		
		 if (a > b) {
	            int temp = a;
	            a = b;
	            b = temp;
	        }
		 
		 if(a>c) {
			 int temp = a;
			 a =c;
			 c = temp;
		 }
		 
		 if(b>c) {
			 int temp = c;
			 b = c;
			 c = temp;
		 }
		 
		 System.out.println("Ordem crescente:" + a +"," + b+"," +c);
		
		

	}

}
