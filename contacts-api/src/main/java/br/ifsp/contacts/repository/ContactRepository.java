package br.ifsp.contacts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifsp.contacts.model.Contact;

/**
 * Esta interface extende JpaRepository, que nos fornece métodos prontos 
 * para acessar e manipular dados no banco de dados. Basta especificar 
 * a classe de entidade (Contact) e o tipo da chave primária (Long).
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	
	
    // Podemos adicionar métodos personalizados se necessário.
	
	//EX1. ADD Busca pelo nome (ignora maiúscula e minúscula)
	List<Contact> findByNomeContainingIgnoreCase(String name);
	
	
}
