package tech.ada.banco.service.operacao.rentabiliza;

import java.math.BigDecimal;

public class JurosPoupanca implements Rentabiliza {

	private static final BigDecimal TX_JUROS = BigDecimal.ONE.add(BigDecimal.valueOf(0.005429));
	
	@Override
	public BigDecimal calcular(BigDecimal valor) {
		return valor.multiply(TX_JUROS);
	}
	
}
