package l2ex19;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite um valor entre 0.00 e 100.00: ");
        String valorStr = scanner.nextLine().trim();

        String[] partes = valorStr.split("\\.");
        String parteInteiraStr = partes[0];
        String parteDecimalStr = (partes.length > 1) ? partes[1] : "00";

        if (parteDecimalStr.length() == 1) parteDecimalStr += "0";
        else if (parteDecimalStr.length() > 2) parteDecimalStr = parteDecimalStr.substring(0, 2);

        int parteInteira, parteDecimal;
        try {
            parteInteira = Integer.parseInt(parteInteiraStr);
            parteDecimal = Integer.parseInt(parteDecimalStr);
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido.");
            return;
        }

        if (parteInteira < 0 || parteInteira > 100 || parteDecimal < 0 || parteDecimal > 99) {
            System.out.println("Valor fora do intervalo permitido.");
            return;
        }

        StringBuilder resultado = new StringBuilder();

        if (parteInteira == 0 && parteDecimal == 0) {
            resultado.append("zero reais");
        } else {
            if (parteInteira > 0) {
                String reais = ConversorNumeros.converterParaExtenso(parteInteira);
                if (reais == null) {
                    System.out.println("Erro na conversão.");
                    return;
                }
                resultado.append(reais);
                resultado.append(parteInteira == 1 ? " real" : " reais");
            }

            if (parteDecimal > 0) {
                String centavos = ConversorNumeros.converterParaExtenso(parteDecimal);
                if (centavos == null) {
                    System.out.println("Erro na conversão.");
                    return;
                }
                if (parteInteira > 0) resultado.append(" e ");
                resultado.append(centavos);
                resultado.append(parteDecimal == 1 ? " centavo" : " centavos");
            }
        }

        System.out.println(resultado);
        scanner.close();
    }
}