package ex14;
import java.util.Arrays;
import java.util.Random;

public class VetorCombinado {

    private int[] vetor1;
    private int[] vetor2;
    private int[] vetorCombinado;

    private static final int TAMANHO_DO_VETOR = 50;

    public VetorCombinado() {
        vetor1 = new int[TAMANHO_DO_VETOR];
        vetor2 = new int[TAMANHO_DO_VETOR];
        vetorCombinado = new int[TAMANHO_DO_VETOR * 2];
    }

    public void gerarVetoresAleatorios() {
        Random random = new Random();

        for(int i = 0; i < TAMANHO_DO_VETOR; i++) {
            vetor1[i] = random.nextInt(100);
        }

        for (int i = 0; i < TAMANHO_DO_VETOR; i++) {
            vetor2[i] = random.nextInt(100);
        }
    }

    public void ordenarVetores() {
        Arrays.sort(vetor1);
        Arrays.sort(vetor2);
    }

    public void combinarVetor() {
        int i = 0; // v1
        int j = 0; // v2
        int k = 0; // v combinado

        while (i < TAMANHO_DO_VETOR && j < TAMANHO_DO_VETOR) {
            if (vetor1[i] <= vetor2[j]) {
                vetorCombinado[k] = vetor1[i];
                i++;
            }
            else {
                vetorCombinado[k] = vetor2[j];
                j++;
            }
            k++;
        }

        // copiar p vetor combinado
        while (i < TAMANHO_DO_VETOR) {
            vetorCombinado[k] = vetor1[i]; // Corrigido: vetor1, não vetor2
            i++;
            k++;
        }
        
        while (j < TAMANHO_DO_VETOR) {
            vetorCombinado[k] = vetor2[j]; // Corrigido: vetor2[j], não vetor2[i]
            j++;
            k++;
        }
    }

    // exibir vetor
    public void exibirVetor(int[] vetor) {
        for (int i = 0; i < vetor.length; i++) {
            System.out.print(vetor[i] + " ");

            if ((i + 1) % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    public int[] getVetor1() {
        return vetor1;
    }

    public int[] getVetor2() {
        return vetor2;
    }

    public int[] getVetorCombinado() {
        return vetorCombinado;
    }
}