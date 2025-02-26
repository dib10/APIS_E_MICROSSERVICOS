package ex07;


import java.util.Scanner;
public class ProgramaB {
 public static void main(String[] args) {
 Scanner teclado = new Scanner(System.in);
 int codigo;

 do {
 System.out.print("Informe o código: ");
 codigo = teclado.nextInt();
 
//Colocamos uma condição em 'do'
 
 if (codigo != -1) {
	 System.out.println("Código: " +codigo);
 }
 System.out.println("Código: " + codigo);
 } while (codigo != -1);
 }
}
