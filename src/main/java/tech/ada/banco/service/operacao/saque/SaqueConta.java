package tech.ada.banco.service.operacao.saque;

import java.math.BigDecimal;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.ada.banco.exception.SaldoIndisponivelException;
import tech.ada.banco.exception.ValorInvalidoException;
import tech.ada.banco.model.Conta;
import tech.ada.banco.repository.ContaRepository;

@Service
@RequiredArgsConstructor
@Primary
public class SaqueConta implements Saque {

	private final ContaRepository repository;

	@Override
	public void sacar(BigDecimal valor, Conta conta) throws SaldoIndisponivelException, ValorInvalidoException {
		BigDecimal saldoInicial = conta.getSaldo();

		if (valor.compareTo(BigDecimal.ZERO)<1)
			throw new ValorInvalidoException("Valor do saque deve ser maior que zero");
		
		if (saldoInicial.compareTo(valor)<0)
			throw new SaldoIndisponivelException("Valor do saque ultrapassa o saldo da conta");
		
		conta.setSaldo(saldoInicial.subtract(valor));
		repository.save(conta);
	}

}
