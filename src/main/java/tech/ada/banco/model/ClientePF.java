package tech.ada.banco.model;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import tech.ada.banco.annotations.IdadeValidation;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@DiscriminatorValue("1")
public class ClientePF extends Cliente {

    @Column(unique = true)
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotNull(message = "Data de nascimento não dever ser nula")
    @IdadeValidation
    private LocalDate dataNascimento;

}
