package tech.ada.banco.service.operacao.saque;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import tech.ada.banco.exception.SaldoIndisponivelException;
import tech.ada.banco.exception.ValorInvalidoException;
import tech.ada.banco.model.ClientePJ;
import tech.ada.banco.model.Conta;
import tech.ada.banco.repository.ContaRepository;
import tech.ada.banco.service.operacao.tarifa.Tarifa;
import tech.ada.banco.service.operacao.tarifa.Tarifavel;

@Service
@Qualifier("saqueComTarifa")
@RequiredArgsConstructor
public class SaqueComTarifa implements Saque<Conta>, Tarifavel {

	private final ContaRepository repository;
	
	private final Tarifa tarifa;

	@Override
	public void sacar(BigDecimal valor, Conta conta) throws SaldoIndisponivelException, ValorInvalidoException {
		BigDecimal saldoInicial = conta.getSaldo();
		BigDecimal taxa = this.calcularTarifa(valor, this.tarifa.getTaxa());

		if (valor.compareTo(BigDecimal.ZERO)<1)
			throw new ValorInvalidoException("Valor do saque deve ser maior que zero");
		
		if (saldoInicial.compareTo(valor.add(taxa))<0)
			throw new SaldoIndisponivelException("Valor do saque ultrapassa o saldo da conta");
		
		conta.setSaldo(saldoInicial.subtract(valor).subtract(taxa));
		repository.save(conta);
		
	}
	
	@Override
	public BigDecimal calcularTarifa(BigDecimal valor, BigDecimal taxa) {
		return valor.multiply(taxa);
	}

}
