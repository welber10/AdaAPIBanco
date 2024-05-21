package tech.ada.banco.service.operacao.tarifa;

import java.math.BigDecimal;

public interface Tarifavel {
	
	BigDecimal calcularTarifa(BigDecimal valor, BigDecimal taxa);

}
