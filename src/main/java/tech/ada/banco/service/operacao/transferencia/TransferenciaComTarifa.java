package tech.ada.banco.service.operacao.transferencia;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.ada.banco.exception.SaldoIndisponivelException;
import tech.ada.banco.exception.ValorInvalidoException;
import tech.ada.banco.model.Conta;
import tech.ada.banco.repository.ContaRepository;
import tech.ada.banco.service.operacao.tarifa.Tarifa;
import tech.ada.banco.service.operacao.tarifa.Tarifavel;

@Service
@RequiredArgsConstructor
public class TransferenciaComTarifa implements Transferencia<Conta>, Tarifavel {

	private final ContaRepository repository;

	private Tarifa tarifa;

	@Override
	public void transferir(BigDecimal valor, Conta contaOrigem, Conta contaDestino) throws SaldoIndisponivelException, ValorInvalidoException {
		BigDecimal saldoInicial = contaOrigem.getSaldo();
		BigDecimal taxa = this.calcularTarifa(valor, this.tarifa.getTaxa());
		
		if (valor.compareTo(BigDecimal.ZERO)<1)
			throw new ValorInvalidoException("Valor da transferência deve ser maior que zero");

		if (saldoInicial.compareTo(valor.add(taxa))<0)
			throw new SaldoIndisponivelException("Valor da transferencia ultrapassa o saldo da conta de origem");
		
		contaOrigem.setSaldo(saldoInicial.subtract(valor).subtract(taxa));
		contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

		repository.save(contaOrigem);
		repository.save(contaDestino);
	}

	@Override
	public BigDecimal calcularTarifa(BigDecimal valor, BigDecimal taxa) {
		return valor.multiply(taxa);
	}

}
