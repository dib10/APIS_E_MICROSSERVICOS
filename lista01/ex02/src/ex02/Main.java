package ex02;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite as coordenadas do primeiro ponto, respectivamente x,y e z");
		double x1 = sc.nextDouble();
		double y1 = sc.nextDouble();
		double z1 = sc.nextDouble();
		
		System.out.println("Digite as coordenadas do segundo ponto, respectivamente x,y e z");
		double x2 = sc.nextDouble();
		double y2 = sc.nextDouble();
		double z2 = sc.nextDouble();
		
		
		//pontos
		Pontos3d p1 = new Pontos3d(x1, y1, z1);
		Pontos3d p2 = new Pontos3d(x2,y2,z2);
		
		double distancia = p1.calcularDistancia(p2); //de p1 a p2
		System.out.println("A distância entre os dois pontos é: " + distancia);
		
	}

}
