package VcRiquinho;

public class RendaVariavel extends ProdutoInvestimento{
	
	private double rendimentoMensalEsperado;
	public RendaVariavel(String nome, String descricao, double rendimentoMensalEsperado) {
		super(nome, descricao);
		this.rendimentoMensalEsperado = rendimentoMensalEsperado;
	}
	@Override
	public double calcularRendimento(int dias, double saldoAlocado) {//saldoalocado é novo
	    return saldoAlocado * (rendimentoMensalEsperado / 30) * dias;
	}
	
	@Override
	public String toString() {
	    return nome + " (Renda Variável - " + (rendimentoMensalEsperado * 100) + "% ao mês)";
	}
	}
	
	
	
	


