package br.ifsp.contacts.controller;

// Imports Spring e Java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType; // Import necessário para @Content
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import br.ifsp.contacts.dto.AddressDTO;
import br.ifsp.contacts.dto.AddressInputDTO;
import br.ifsp.contacts.exception.ResourceNotFoundException;
import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.AddressRepository;
import br.ifsp.contacts.repository.ContactRepository;

// Imports do Swagger/OpenAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/contacts")

@Tag(name = "Contatos", description = "Endpoints para Gerenciamento de Contatos e seus Endereços")
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
    @Operation(summary = "Cria um novo endereço para um contato", description = "Cadastra um novo endereço associado a um contato existente especificado pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso",
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = AddressDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para o endereço", content = @Content),
        @ApiResponse(responseCode = "404", description = "Contato não encontrado com o ID fornecido", content = @Content)
    })
    public AddressDTO createAddress(
            @Parameter(description = "ID do contato ao qual o endereço será associado", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                 description = "Objeto do endereço a ser criado.", required = true,
                 content = @Content(schema = @Schema(implementation = AddressInputDTO.class)) // Usa o DTO de entrada aqui
            )
            @Valid @RequestBody AddressInputDTO addressInputDto) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("O contato não foi encontrado: "  +id));

        // Converter o DTO para a entidade Address
        Address address = toAddressEntity(addressInputDto);
        address.setContact(contact);
        Address savedAddress = addressRepository.save(address);
        return toAddressDTO(savedAddress);
    }

   
    @GetMapping("/{id}/addresses")
    @Operation(summary = "Lista os endereços de um contato", description = "Retorna uma lista paginada e ordenada dos endereços associados a um contato específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de endereços recuperada com sucesso",
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = Page.class))), 
        
        @ApiResponse(responseCode = "404", description = "Contato não encontrado com o ID fornecido", content = @Content)
    })
    public Page<AddressDTO> getAddressesForContact(
            @Parameter(description = "ID do contato cujos endereços devem ser listados", required = true, example = "1")
            @PathVariable Long id,
            Pageable pageable) { 
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));

        Page<Address> addressesPage = addressRepository.findByContact(contact, pageable);

        return addressesPage.map(this::toAddressDTO);
    }
}