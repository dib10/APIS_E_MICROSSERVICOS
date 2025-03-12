package VcRiquinho;

import java.util.ArrayList;
import java.util.List;

public abstract class Cliente {
	protected String nome;
	protected String email;
	protected List<Conta> contas = new ArrayList<>();
	
	public Cliente(String nome, String email) {
		super();
		this.nome = nome;
		this.email = email;
	}
	//CRUD
	public void adicionarConta(Conta conta) {
			contas.add(conta);
	}
	
	public void removerConta(Conta conta) {
	    if (contas.size() > 1) {
	        contas.remove(conta);
	    } else {
	        System.out.println("O cliente deve ter pelo menos uma conta.");
	    }
	}

	
	public List<Conta> getContas() {
		return contas;
	}
	
	public abstract double calcularTaxaDeServico(double rendimentoBruto);
	
	//gets e setters
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
