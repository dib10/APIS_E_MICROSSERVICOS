package ex13;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] vetor = new int[100];
        Random random = new Random();

        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = random.nextInt(1000); 
        }

        System.out.println("Vetor antes da ordenação:");
        exibirVetor(vetor);

        Algoritmo.quickSort(vetor, 0, vetor.length - 1);

        System.out.println("\nVetor após a ordenação:");
        exibirVetor(vetor);
    }

    
    public static void exibirVetor(int[] vetor) {
        for (int i = 0; i < vetor.length; i++) {
            System.out.print(vetor[i] + " ");
            if ((i + 1) % 10 == 0) {
                System.out.println();
            }
        }
    }


	}


