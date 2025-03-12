package VcRiquinho;

public class ContaCorrente extends Conta{

	public ContaCorrente(double saldoInicial) {
		super(saldoInicial);
	}
	@Override
	public double calcularRendimentoBruto(int dias) {
		return 0; //retornar 0, pois não rende
	}
}
