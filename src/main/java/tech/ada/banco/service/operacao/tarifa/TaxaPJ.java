package tech.ada.banco.service.operacao.tarifa;

import java.math.BigDecimal;

import org.springframework.context.annotation.Primary;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Primary
public class TaxaPJ implements Tarifa {

	@Override
	public BigDecimal getTaxa() {
		return BigDecimal.valueOf(0.005);
	}

}
