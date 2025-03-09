package l2ex29;


public class TabelaVerdade {
	
	public static String converterParaVF(boolean valor) {
		if(valor) {
			return "V";
		}
		else {
			return "F";
		}
	}
	
	
	//gerar linha da tabela
	
	public static String gerarLinha(boolean a, boolean b, boolean c) {
		String linha = converterParaVF(a) + "|" + converterParaVF(b) + "|" + converterParaVF(c) + "|" + converterParaVF(a || b || c);
		
		return linha;
	}

}
