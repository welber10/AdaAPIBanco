package tech.ada.banco.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContaDTO {

	private Long id;

	@Positive(message = "Saldo da conta deve ser maior que zero")
    private BigDecimal saldo;

    @PastOrPresent
	private LocalDateTime dataCriacao;

	@NotNull
	private Long clienteId;

}
