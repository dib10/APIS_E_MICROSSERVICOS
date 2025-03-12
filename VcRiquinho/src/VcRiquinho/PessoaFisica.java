package VcRiquinho;

public class PessoaFisica extends Cliente{
	
	private String cpf;

	public PessoaFisica(String nome, String email, String cpf) {
		super(nome, email);
		this.cpf = cpf;
	}
	@Override
	public double calcularTaxaDeServico(double rendimentoBruto) {
		// TODO Auto-generated method stub
		return rendimentoBruto * 0.001; //pf -> taxa 0.1%
	}

}
