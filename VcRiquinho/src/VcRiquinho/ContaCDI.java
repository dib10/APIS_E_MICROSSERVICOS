package VcRiquinho;

public class ContaCDI extends Conta {
    private static final double CDI = 0.10; // 10% ao ano

    public ContaCDI(double saldoInicial) {
        super(saldoInicial);
    }
    // Retorna o rendimento BRUTO (sem desconto de taxa)
    
    @Override
    public double calcularRendimentoBruto(int dias) {
        return saldo * (CDI / 365) * dias; 
    }
    // Cálculo da taxa  CDI
    public double calcularTaxaCDI(int dias) {
        double rendimentoBruto = calcularRendimentoBruto(dias);
        return rendimentoBruto * 0.0007; // taxa de serviço fixa de 0,07%
    }
}