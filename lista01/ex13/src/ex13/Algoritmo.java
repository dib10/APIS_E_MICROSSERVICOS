package ex13;

public class Algoritmo {
	
	public static void quickSort(int[] vetor, int inicio, int fim)
	{
		if(inicio < fim) {
			int p = particionar(vetor, inicio, fim);
			quickSort(vetor,inicio,p-1);
			quickSort(vetor,p+1,fim);
		}
	}
	
	public static int particionar(int[] vetor,int inicio,int fim) {
		int pivo = vetor[fim];
		int i = inicio -1;
		for(int j = inicio; j<fim;j++) {
			if (vetor[j]<=pivo) {
				i++;
				int temp = vetor[i];
				vetor[i] = vetor[j];
				vetor[j] = temp;
			}
		}
		int temp = vetor[i+1];
		vetor[i+1]  = vetor[fim];
		vetor[fim]= temp;
		return i+1;
	}

}
