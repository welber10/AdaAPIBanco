package tech.ada.banco.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tech.ada.banco.annotations.IdadeValidation;
import tech.ada.banco.enums.Status;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClienteDTO {

    private Long id;

    @NotEmpty(message = "Nome não dever ser vazio")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Column(unique = true)
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotNull(message = "Data de nascimento não dever ser nula")
    @IdadeValidation
    private LocalDate dataNascimento;

    private Status status;

}
