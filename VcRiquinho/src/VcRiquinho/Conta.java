package VcRiquinho;

public abstract class Conta {
    
    protected double saldo;

    public Conta(double saldoInicial) {
        this.saldo = saldoInicial;
    }
    
    public abstract double calcularRendimentoBruto(int dias);
}
