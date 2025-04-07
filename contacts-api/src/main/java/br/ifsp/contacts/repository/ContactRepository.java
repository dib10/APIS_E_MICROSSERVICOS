package br.ifsp.contacts.repository;

// Imports adicionados:
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// import java.util.List; // Removido ou comentado se não for mais necessário
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
    // List<Contact> findByNomeContainingIgnoreCase(String name); // <- Método original comentado/removido

    Page<Contact> findByNomeContainingIgnoreCase(String name, Pageable pageable); // <- Assinatura modificada para paginação
}