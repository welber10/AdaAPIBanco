package tech.ada.banco.service.operacao.saldo;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.ada.banco.dto.ContaDTO;

@Service
@RequiredArgsConstructor
public class ConsultaSaldo implements Saldo {

	@Override
	public BigDecimal consultar(ContaDTO conta) {
		return conta.getSaldo();
	}

}
