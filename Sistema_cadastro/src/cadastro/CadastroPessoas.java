package cadastro;

import java.util.ArrayList;

public class CadastroPessoas {
	
	ArrayList<Pessoa> pessoas = new ArrayList<>();

	public void cadastraPessoa(Pessoa pe) {
		pessoas.add(pe);
		
	}
	
	public void imprimeCadastro() {
		for(Pessoa p: pessoas) {
			p.imprimeDados();
		}
	}

}
