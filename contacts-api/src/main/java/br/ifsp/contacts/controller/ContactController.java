package br.ifsp.contacts.controller;

// Imports do Spring e Java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType; 
import org.springframework.web.bind.annotation.*; // Wildcard para annotations
import org.springframework.transaction.annotation.Transactional; 
import jakarta.validation.Valid;

import br.ifsp.contacts.dto.ContactDTO; // importando o DTO
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
//importação para @Valid //
/**
 * Classe responsável por mapear as rotas/endpoints relacionados
 * aos contatos. Cada método abaixo corresponde a uma operação
 * em nosso sistema.
 *
 * @RestController: Indica que esta classe é um controlador
 * responsável por responder requisições REST.
 * @RequestMapping("/api/contacts"): Indica o caminho base.
 */
@RestController
@RequestMapping("/api/contacts")
@Tag(name = "Contatos", description = "Endpoints para Gerenciamento de Contatos") // Tag para agrupar no Swagger UI
public class ContactController {

    /**
     * @Autowired permite que o Spring "injete" automaticamente
     * uma instância de ContactRepository aqui,
     * sem que precisemos criar manualmente.
     */
    @Autowired
    private ContactRepository contactRepository;

    // Injeção do AddressRepository necessária para o delete
    @Autowired
    private AddressRepository addressRepository;

    //**************** MAPEAMENTO ****************

    private ContactDTO toContactDTO(Contact contact) {
        return new ContactDTO (
                contact.getId(),
                contact.getNome(),
                contact.getTelefone(),
                contact.getEmail()
                );
    }

    private Contact toContactEntity(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setNome(contactDTO.getNome());
        contact.setTelefone(contactDTO.getTelefone());
        contact.setEmail(contactDTO.getEmail());
        return contact;
    }

    //**************** MAPEAMENTO ****************


    /**
     * Método para obter todos os contatos. // Seu comentário preservado
     *
     * @GetMapping 
     */
    @GetMapping
    @Operation(summary = "Lista todos os contatos", description = "Retorna uma lista paginada e ordenada de todos os contatos cadastrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de contatos recuperada com sucesso",
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = Page.class)))
    })
    public Page<ContactDTO> getAllContacts(Pageable pageable) {
        Page<Contact> contactPage = contactRepository.findAll(pageable);
        return contactPage.map(this::toContactDTO);
    }

    
    @GetMapping("/search")
    @Operation(summary = "Busca contatos por nome", description = "Retorna uma lista paginada de contatos cujo nome contenha o termo de busca (ignorando maiúsculas/minúsculas).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contatos encontrados",
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = Page.class)))
    })
    public Page<ContactDTO> searchContactsByName(
            @Parameter(description = "Termo para buscar no nome do contato", required = true, example = "Silva")
            @RequestParam String name,
            Pageable pageable) {
        Page<Contact> contactPage = contactRepository.findByNomeContainingIgnoreCase(name, pageable);
        return contactPage.map(this::toContactDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um contato por ID", description = "Retorna os detalhes de um contato específico baseado no seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contato encontrado",
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = ContactDTO.class))),
        @ApiResponse(responseCode = "404", description = "Contato não encontrado",
                     content = @Content)
    })
    public ContactDTO getContactById(
            @Parameter(description = "ID do contato a ser buscado", required = true, example = "1")
            @PathVariable Long id) {
        Contact contact = contactRepository.findById(id)
                // exceção personalizada //
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado com ID: " + id));
        return toContactDTO(contact); // Converte a entidade encontrada para DTO // 
    }

    /**
     * Método para criar um novo contato.
     * @Valid // 
     * @PostMapping indica que este método responde a chamadas HTTP POST. 
     * @RequestBody indica que o objeto Contact será preenchido // 
     * com os dados JSON enviados no corpo da requisição. // 
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria um novo contato", description = "Cadastra um novo contato no sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Contato criado com sucesso",
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = ContactDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                     content = @Content)
    })
    public ContactDTO createContact(
             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                 description = "Objeto do contato a ser criado. ID é ignorado.", required = true,
                 content = @Content(schema = @Schema(implementation = ContactDTO.class))
            )
            @Valid @RequestBody ContactDTO contactDTO) {
        Contact contactToSave = toContactEntity(contactDTO);
        Contact savedContact = contactRepository.save(contactToSave);
        return toContactDTO(savedContact);
    }

    /**
     * Método para atualizar um contato existente.
     *
     * @PutMapping indica que este método responde a chamadas HTTP PUT. // Seu comentário preservado
     * Exemplo de acesso: PUT /api/contacts/1 //
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um contato existente", description = "Atualiza todos os dados de um contato existente baseado no seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contato atualizado com sucesso",
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = ContactDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Contato não encontrado", content = @Content)
    })
    public ContactDTO updateContact(
            @Parameter(description = "ID do contato a ser atualizado", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                 description = "Objeto do contato com os dados atualizados.", required = true,
                 content = @Content(schema = @Schema(implementation = ContactDTO.class))
            )
            @Valid @RequestBody ContactDTO contactDTO) {
        // Buscar o contato existente //
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));

        // Atualiza os campos da entidade existente com os dados do DTO //
        existingContact.setNome(contactDTO.getNome());
        existingContact.setTelefone(contactDTO.getTelefone());
        existingContact.setEmail(contactDTO.getEmail());

        // Salva a entidade atualizada 
        Contact updatedContact = contactRepository.save(existingContact);

        // Retorna o DTO da entidade atualizada 
        return toContactDTO(updatedContact);
    }

    /**
     * Método para excluir um contato pelo ID, incluindo seus endereços.
     * (Comentário original preservado e atualizado) 
     * @DeleteMapping indica que este método responde a chamadas HTTP DELETE. 
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui um contato", description = "Exclui um contato e todos os seus endereços associados baseado no seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Contato excluído com sucesso", content = @Content),
        @ApiResponse(responseCode = "404", description = "Contato não encontrado", content = @Content)
    })
    @Transactional 
    public void deleteContact(
            @Parameter(description = "ID do contato a ser excluído", required = true, example = "1")
            @PathVariable Long id) {
        // 1. Buscar o contato ou lançar exceção se não existir // 
        Contact contact = contactRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado com ID: " + id));

         
        List<Address> addressesToDelete = addressRepository.findByContact(contact, Pageable.unpaged()).getContent(); 

         
        if (!addressesToDelete.isEmpty()) {
            addressRepository.deleteAll(addressesToDelete);
        }

        contactRepository.delete(contact);
    }

    //EX2. ADD PATCH para atualizar o email /
    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza parcialmente um contato", description = "Atualiza apenas os campos fornecidos de um contato existente (nome, telefone e/ou email).")
     @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contato atualizado parcialmente com sucesso",
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = ContactDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos (ex: email mal formatado)", content = @Content),
        @ApiResponse(responseCode = "404", description = "Contato não encontrado", content = @Content)
    })
    public ContactDTO patchContact(
            @Parameter(description = "ID do contato a ser atualizado parcialmente", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                 description = "Objeto do contato com os campos a serem atualizados. Campos omitidos ou nulos não serão alterados.", required = true,
                 content = @Content(schema = @Schema(implementation = ContactDTO.class))
            )
            @Valid @RequestBody ContactDTO patchContactDTO) {

        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));
        //atualizar apenas os que não são nulos 
        if (patchContactDTO.getNome() != null) {
            existingContact.setNome(patchContactDTO.getNome());
        }

        if (patchContactDTO.getTelefone() != null) {
            existingContact.setTelefone(patchContactDTO.getTelefone());
        }
        if (patchContactDTO.getEmail() != null) {
            existingContact.setEmail(patchContactDTO.getEmail());
        }

        Contact savedContact = contactRepository.save(existingContact);

        return toContactDTO(savedContact);
    }
}