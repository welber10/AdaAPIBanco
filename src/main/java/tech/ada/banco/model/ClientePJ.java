package tech.ada.banco.model;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

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
@Entity
@DiscriminatorValue("2")
public class ClientePJ extends Cliente {

    @Column(unique = true)
    @CNPJ(message = "CNPJ inválido")
    private String cnpj;

}
