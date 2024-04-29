package tech.ada.banco.service.operacao.deposito;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.ada.banco.exception.ValorInvalidoException;
import tech.ada.banco.model.Conta;
import tech.ada.banco.repository.ContaRepository;

@Service
@RequiredArgsConstructor
public class DepositoConta implements Deposito {

	private final ContaRepository repository;

	@Override
	public void depositar(BigDecimal valor, Conta conta) throws ValorInvalidoException {
		if (valor.compareTo(BigDecimal.ZERO)<1)
			throw new ValorInvalidoException("Valor do deposito deve ser maior que zero");

		conta.setSaldo(conta.getSaldo().add(valor));
		repository.save(conta);
	}

}
