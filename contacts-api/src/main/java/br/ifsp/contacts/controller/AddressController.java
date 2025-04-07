package br.ifsp.contacts.controller;

// Imports necessários (Page, Pageable adicionados, List/Collectors removidos se não usados em outros métodos)
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.ifsp.contacts.dto.AddressInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.ifsp.contacts.dto.AddressDTO;
import br.ifsp.contacts.exception.ResourceNotFoundException;
import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.AddressRepository;
import br.ifsp.contacts.repository.ContactRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contacts")
public class AddressController {

    //o autowired permite que o spring injete de forma automatica uma instancia do AddressRepository, e também permite injetar no ContactRepository
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactRepository contactRepository;


    // *******MAPEAMENTO **

    private AddressDTO toAddressDTO(Address address) {
        // verifica se o contato não é nulo antes de pegar o id
        Long contactId = (address.getContact() != null) ? address.getContact().getId() : null;
        return new AddressDTO(
                address.getId(),
                address.getRua(),
                address.getCidade(),
                address.getEstado(),
                address.getCep(),
                contactId
        );
    }

    // DTO  de entrada para a entidade

    private Address toAddressEntity(AddressInputDTO addressInputDTO) {
        Address address = new Address();
        address.setRua(addressInputDTO.getRua());
        address.setCidade(addressInputDTO.getCidade());
        address.setEstado(addressInputDTO.getEstado());
        address.setCep(addressInputDTO.getCep());

        return address;
    }

    // ******* MAPEAMENTO *********


    @PostMapping("/{id}/addresses")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDTO createAddress(@PathVariable Long id, @Valid @RequestBody AddressInputDTO addressInputDto) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("O contato não foi encontrado: "  +id));

        // Converter o DTO para a entidade Address
        Address address = toAddressEntity(addressInputDto);
        address.setContact(contact);
        Address savedAddress = addressRepository.save(address);
        return toAddressDTO(savedAddress);
    }

    /**
     * adicionando método que todos os endereços do contato de forma paginada
     * Exemplo: GET /api/contacts/1/addresses?page=0&size=5&sort=cidade
     */
    @GetMapping("/{id}/addresses")
    // Assinatura corrigida: recebe Pageable, retorna Page<AddressDTO>
    public Page<AddressDTO> getAddressesForContact(@PathVariable Long id, Pageable pageable) {
        // 1. Busca o contato (ou lança exceção)
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));

        // 2. Chama o método do repositório com Pageable, retorna Page<Address>
        Page<Address> addressesPage = addressRepository.findByContact(contact, pageable);

        // 3. Mapeia Page<Address> para Page<AddressDTO> usando map() e retorna
        return addressesPage.map(this::toAddressDTO);
    }
}