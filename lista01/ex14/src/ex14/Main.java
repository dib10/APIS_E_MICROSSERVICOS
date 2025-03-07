package ex14;

public class Main {
    public static void main(String[] args) {
        VetorCombinado vetores = new VetorCombinado();
        vetores.gerarVetoresAleatorios();
        
        System.out.println("Vetor 1 (Original):");
        vetores.exibirVetor(vetores.getVetor1());
        System.out.println("\nVetor 2 (Original):");
        vetores.exibirVetor(vetores.getVetor2());
        
        vetores.ordenarVetores();
        
        System.out.println("\nVetor 1 (Ordenado):");
        vetores.exibirVetor(vetores.getVetor1());
        System.out.println("\nVetor 2 (Ordenado):");
        vetores.exibirVetor(vetores.getVetor2());
        
        vetores.combinarVetor();
        
        System.out.println("\nVetor Combinado (Ordenado):");
        vetores.exibirVetor(vetores.getVetorCombinado());
    }
}