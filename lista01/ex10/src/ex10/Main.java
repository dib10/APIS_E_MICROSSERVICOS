package ex10;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		double investimentoMes;
		double jurosMes;
		
		System.out.println("Valor a ser investido por mês");
		investimentoMes = sc.nextDouble();
		
		System.out.println("Informe a taxa por mês (%)");
		jurosMes = sc.nextDouble();
		
		Investimento investimento = new Investimento(investimentoMes, jurosMes);
		
		do {
			double saldoTotal = investimento.calcularSaldoAnual();
			System.out.println("Saldo após 1 ano de investimento:" + saldoTotal);
			System.out.println("Deseja processar mais um ano? (S/N)");
			String escolha = sc.next();
			if(!escolha.equalsIgnoreCase("S")) {
				break; //para o programa
			}
		} while (true);
	}
		
	}

