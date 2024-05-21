package tech.ada.banco.service.operacao.rentabiliza;

import java.math.BigDecimal;

public class RendimentoInvestimentoPJ implements Rentabiliza {

	/**
	 * Clientes PJ possuem rendimentos de 2% no momento do investimento.
	 */
	private static final BigDecimal TX_JUROS = BigDecimal.valueOf(0.02);
	
	@Override
	public BigDecimal calcular(BigDecimal valor) {
		return valor.multiply(TX_JUROS);
	}

}
