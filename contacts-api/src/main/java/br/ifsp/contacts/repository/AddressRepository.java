package br.ifsp.contacts.repository;

// Imports adicionados:
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List; // Mantido pois ainda é usado em deleteContact no Controller
import org.springframework.data.jpa.repository.JpaRepository;
import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.model.Contact;

public interface AddressRepository extends JpaRepository<Address, Long> {

    //n esquecer de add metódo aq

    // List<Address> findByContact(Contact contact); // <- Método original comentado/removido

    Page<Address> findByContact(Contact contact, Pageable pageable); // <- Assinatura modificada para paginação
}