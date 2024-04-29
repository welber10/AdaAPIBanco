package tech.ada.banco.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioDTO {

    private Long id;

    @NotBlank
    private String nome;

    @Email (message = "Email inválido")
    private String email;

    @CPF(message = "CPF inválido")
    private String cpf;

    private String telefone;
    
    private String username;
    
    private String password;

    private String roles;
    
}
