package cadastro;

class Cliente extends Pessoa {
	
	int codigo;

	public Cliente(String nome, Data nascimento, int codigo) {
		// TODO Auto-generated constructor stub
		super(nome, nascimento); //chamando o construtor da classe pai
		this.codigo = codigo;
		
	}
	
	@Override
	
	public void imprimeDados() {
		System.out.println("Cliente: " + nome + ", Nascimento: " + 
		nascimento +", Código: " + codigo);
	}

}
