package br.ifsp.contacts.model;

import jakarta.persistence.*;

@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	
	private long id;
	private String rua;
	private String cidade;
	private String estado;
	private String cep;
	
	//define um relacionamento muitos pra um com a entity CONTACT
	@ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

	//construtor

	public Address(String rua, String cidade, String estado, String cep, Contact contact) {
		super();
		this.rua = rua;
		this.cidade = cidade;
		this.estado = estado;
		this.cep = cep;
		this.contact = contact;
	}
	//gets setts
	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public long getId() {
		return id;
	}
	
	
	
	
	
	
	

}
