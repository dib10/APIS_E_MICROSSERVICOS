package br.ifsp.contacts.controller;

import br.ifsp.contacts.model.Address; // Import necessário
import br.ifsp.contacts.repository.AddressRepository; // Import necessário
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional; // Import necessário
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.ifsp.contacts.dto.ContactDTO; // importando o DTO
import br.ifsp.contacts.exception.ResourceNotFoundException;
import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.ContactRepository;
//importação para @Valid

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
     * Método para obter todos os contatos.
     *
     * @GetMapping indica que este método vai responder a chamadas HTTP GET.
     * Exemplo de acesso: GET /api/contacts
     */
    @GetMapping
    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll()
                .stream() // Converte a lista para um stream
                .map(this::toContactDTO) // Mapeia cada Contact para ContactDTO usando nosso método
                .collect(Collectors.toList()); // Coleta os resultados em uma nova lista
    }

    @GetMapping("/search")
    // EX 1 Método para buscar contatos por nome
    public List<ContactDTO> searchContactsByName(@RequestParam String name) {
        return contactRepository.findByNomeContainingIgnoreCase(name)
                .stream()
                .map(this::toContactDTO)
                .collect(Collectors.toList());
    }

    /**
     * Método para obter um contato específico pelo seu ID.
     *
     * @PathVariable "amarra" a variável {id} da URL
     * ao parâmetro do método.
     * Exemplo de acesso: GET /api/contacts/1
     */
    @GetMapping("/{id}")
    public ContactDTO getContactById(@PathVariable Long id) {
        Contact contact = contactRepository.findById(id)
                // exceção personalizada
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado com ID: " + id));
        return toContactDTO(contact); // Converte a entidade encontrada para DTO
    }

    /**
     * Método para criar um novo contato.
     * @Valid
     * @PostMapping indica que este método responde a chamadas HTTP POST.
     * @RequestBody indica que o objeto Contact será preenchido
     * com os dados JSON enviados no corpo da requisição.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDTO createContact(@Valid @RequestBody ContactDTO contactDTO) {
        Contact contactToSave = toContactEntity(contactDTO);
        Contact savedContact = contactRepository.save(contactToSave);

        return toContactDTO(savedContact);
    }

    /**
     * Método para atualizar um contato existente.
     *
     * @PutMapping indica que este método responde a chamadas HTTP PUT.
     * Exemplo de acesso: PUT /api/contacts/1
     */
    @PutMapping("/{id}")
    public ContactDTO updateContact(@PathVariable Long id, @Valid @RequestBody ContactDTO contactDTO) {
        // Buscar o contato existente
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));

        // Atualiza os campos da entidade existente com os dados do DTO
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
     * Exemplo de acesso: DELETE /api/contacts/1
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional // Garante operação atômica no banco
    public void deleteContact(@PathVariable Long id) {
        // 1. Buscar o contato ou lançar exceção se não existir
        Contact contact = contactRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado com ID: " + id));

        // 2. Buscar os endereços associados a este contato
        List<Address> addressesToDelete = addressRepository.findByContact(contact);

        // 3. Deletar os endereços associados PRIMEIRO (se existirem)
        if (!addressesToDelete.isEmpty()) {
            addressRepository.deleteAll(addressesToDelete);
        }

        // 4. DEPOIS deletar o contato
        contactRepository.delete(contact);
    }

    //EX2. ADD PATCH para atualizar o email
    @PatchMapping("/{id}")
    public ContactDTO patchContact(@PathVariable Long id, @ Valid @RequestBody ContactDTO patchContactDTO) {

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