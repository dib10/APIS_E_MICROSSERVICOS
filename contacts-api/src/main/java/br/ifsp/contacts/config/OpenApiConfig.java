package br.ifsp.contacts.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration 
@OpenAPIDefinition( 
    info = @Info(
        title = "Contacts API", 
        version = "v1.0", 
        description = "API para Gerenciamento de Contatos e Endereços", 
        contact = @Contact( 
            name = "Caio Dib Laronga",
            email = "caio.dib@aluno.ifsp.edu.br",
            url = "https://github.com/dib10/APIS_E_MICROSSERVICOS/tree/main/contacts-api" 
        )
       
    )
    
)
public class OpenApiConfig {
    
}