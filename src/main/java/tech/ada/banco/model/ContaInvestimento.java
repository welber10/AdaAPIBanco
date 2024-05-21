package tech.ada.banco.model;

import java.math.BigDecimal;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import tech.ada.banco.service.operacao.rentabiliza.Rentabiliza;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@DiscriminatorValue("2")
public class ContaInvestimento extends Conta {

    private BigDecimal investimento = BigDecimal.ZERO;
	
    @Transient
	private Rentabiliza rentabilidade;

}
