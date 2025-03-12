package VcRiquinho;

public abstract class Conta {
    
    protected double saldo;

    public Conta(double saldoInicial) {
        this.saldo = saldoInicial;
    }
    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public abstract double calcularRendimentoBruto(int dias);
}
