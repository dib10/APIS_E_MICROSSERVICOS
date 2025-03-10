package VcRiquinho;

public class PessoaJuridica extends Cliente {
	private String cnpj;

	public PessoaJuridica(String nome, String email, String cnpj) {
		super(nome, email);
		this.cnpj = cnpj;
	}

	@Override
	public double calcularTaxaDeServico(double rendimentoBruto) {
		// TODO Auto-generated method stub
		return rendimentoBruto * 0.0015;
	}

	
}
