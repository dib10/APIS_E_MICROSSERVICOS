
package br.ifsp.contacts.dto;
public class AddressDTO {
	
	private long id;
	private String rua;
	private String cidade;
	private String estado;
	private String cep;
	private Long contactId; //referenciar contato pelo id
	
	public AddressDTO() {
	}

	public AddressDTO(long id, String rua, String cidade, String estado, String cep, Long contactId) {
		super();
		this.id = id;
		this.rua = rua;
		this.cidade = cidade;
		this.estado = estado;
		this.cep = cep;
		this.contactId = contactId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	
	
	
	

}
