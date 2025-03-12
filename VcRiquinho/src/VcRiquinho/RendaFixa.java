package VcRiquinho;

public class RendaFixa extends ProdutoInvestimento{
	
	private double rendimentoMensalFixo;
	private int periodoDeCarencia;
	public RendaFixa(String nome, String descricao, double rendimentoMensalFixo, int periodoDeCarencia) {
		super(nome, descricao);
		this.rendimentoMensalFixo = rendimentoMensalFixo;
		this.periodoDeCarencia = periodoDeCarencia;
	}
	
	
	@Override
	public double calcularRendimento(int dias, double saldoAlocado) {//saldo alocado é novo
	    if (dias < periodoDeCarencia) return 0;
	    return saldoAlocado * rendimentoMensalFixo * (dias / 30.0);
	}
	
	public double getRendimentoMensalFixo() {
	    return rendimentoMensalFixo;
	}

	public int getPeriodoDeCarencia() {
	    return periodoDeCarencia;
	}
	
	
	@Override
	
	//toString
	public String toString() {
	    return nome + " (Renda Fixa - " + (rendimentoMensalFixo * 100) + "% ao mês)";
	}

}
