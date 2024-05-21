package tech.ada.banco.service.operacao.investimento;

import java.math.BigDecimal;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.ada.banco.exception.SaldoIndisponivelException;
import tech.ada.banco.model.ContaInvestimento;
import tech.ada.banco.repository.ContaRepository;

@Service
@RequiredArgsConstructor
@Primary
public class InvestimentoComRendimento implements Investimento<ContaInvestimento> {

	private final ContaRepository repository;

	@Override
	public void investir(BigDecimal valor, ContaInvestimento conta) throws SaldoIndisponivelException {
		BigDecimal saldoInicial = conta.getSaldo();
		
		if (saldoInicial.compareTo(valor)<0)
			throw new SaldoIndisponivelException("Valor a ser investido ultrapassa o saldo disponível da conta investimento.");
		
		conta.setSaldo(saldoInicial.subtract(valor));
		conta.setInvestimento(conta.getInvestimento().add(valor).add(conta.getRentabilidade().calcular(valor)));
		repository.save(conta);
	}
	
}
