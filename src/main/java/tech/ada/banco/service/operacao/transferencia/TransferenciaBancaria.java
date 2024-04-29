package tech.ada.banco.service.operacao.transferencia;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.ada.banco.exception.SaldoIndisponivelException;
import tech.ada.banco.exception.ValorInvalidoException;
import tech.ada.banco.model.Conta;
import tech.ada.banco.repository.ContaRepository;

@Service
@RequiredArgsConstructor
public class TransferenciaBancaria implements Transferencia {

	private final ContaRepository repository;

	@Override
	public void transferir(BigDecimal valor, Conta contaOrigem, Conta contaDestino) throws SaldoIndisponivelException, ValorInvalidoException {
		BigDecimal saldoInicial = contaOrigem.getSaldo();
		
		if (valor.compareTo(BigDecimal.ZERO)<1)
			throw new ValorInvalidoException("Valor da transferência deve ser maior que zero");

		if (saldoInicial.compareTo(valor)<0)
			throw new SaldoIndisponivelException("Valor da transferencia ultrapassa o saldo da conta de origem");
		
		contaOrigem.setSaldo(saldoInicial.subtract(valor));
		contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

		repository.save(contaOrigem);
		repository.save(contaDestino);
	}

}
