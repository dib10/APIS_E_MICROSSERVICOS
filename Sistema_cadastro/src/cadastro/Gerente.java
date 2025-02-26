package cadastro;

class Gerente extends Funcionario {
	String area;

	public Gerente(String nome, Data nascimento, float salario, String area) {
		super(nome, nascimento, salario);
		this.area = area;
	}
	
	@Override
	
	public float calculaImposto() {
		return salario * 0.05f; // 5% de imposto sob o salario do gerente
	}
	
	@Override 
    public void imprimeDados() {
        System.out.println("Gerente: " + nome + ", Nascimento: " + nascimento + ", Salário: R$" + salario + ", Área: " + area + ", Imposto: R$" + calculaImposto());
    }

}
