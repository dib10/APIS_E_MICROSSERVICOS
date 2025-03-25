package br.ifsp.contacts.controller;
import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.AddressRepository;
import br.ifsp.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/contacts")
public class AddressController {
	
	//o autowired permite que o spring injete de forma automatica uma instancia do AddressRepository, e também permite injetar no ContactRepository

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactRepository contactRepository;

    // criando método para adicionar endereço ao contato
    @PostMapping("/{id}/addresses")
    public Address createAddress(@PathVariable Long id, @RequestBody Address address) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("O contato não foi encontrado: "  +id));

        // associando o endereço ao contato
        address.setContact(contact);

        
        
        return addressRepository.save(address);
    }

    // adicionando método que todos os endereços do contato
    @GetMapping("/{id}/addresses")
    public List<Address> getAddressesForContact(@PathVariable Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado: " + id));

        
        return addressRepository.findByContact(contact);
    }
}
