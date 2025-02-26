package ex10;

public class Investimento {
	
	private double investimentoMes;
	private double jurosMensal;
	private double saldoTotal;
	private int anos;
	
	//construtor
	
	public Investimento(double investimentoMes, double jurosMensalPercentual) {
		this.investimentoMes = investimentoMes;
		this.jurosMensal = jurosMensalPercentual/100;
		this.saldoTotal = 0.0;
		this.anos = 0;
	}
	
	//métodos
	
	public double calcularSaldoAnual() {
		
		for(int mes = 0;mes <12; mes++ ) {
			saldoTotal = saldoTotal + investimentoMes;
			saldoTotal = saldoTotal * (1+jurosMensal);
			
		}
		anos++;
		return saldoTotal;
		
	}
	
	

}
