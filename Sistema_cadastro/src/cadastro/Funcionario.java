package cadastro;

public class Funcionario extends Pessoa {
	
	float salario;

	public Funcionario(String nome, Data nascimento, float salario) {
		// TODO Auto-generated constructor stub
		super(nome, nascimento);
		this.salario = salario;
	}
	
	public float calculaImposto() { //implm. da lógica do imposto
		// o imposto é 3% do salário
		return salario * 0.03f; // ponto flutuante
		
		
	}
	
	@Override
    public void imprimeDados() {
        System.out.println("Funcionário: " + nome + ", Nascimento: " + nascimento + ", Salário: R$" + salario + ", Imposto: R$" + calculaImposto());
    }

}
