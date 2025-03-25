package br.ifsp.contacts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.model.Contact;




public interface AddressRepository extends JpaRepository<Address, Long> {
	
	//n esquecer de add metódo aq
	
	List<Address> findByContact(Contact contact);

}
