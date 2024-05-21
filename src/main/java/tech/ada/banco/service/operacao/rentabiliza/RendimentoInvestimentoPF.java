package tech.ada.banco.service.operacao.rentabiliza;

import java.math.BigDecimal;

public class RendimentoInvestimentoPF implements Rentabiliza {

	/**
	 * Clientes PF possuem rendimentos de 1% no momento do investimento.
	 */
	private static final BigDecimal TX_JUROS = BigDecimal.valueOf(0.01);
	
	@Override
	public BigDecimal calcular(BigDecimal valor) {
		return valor.multiply(TX_JUROS);
	}

}
