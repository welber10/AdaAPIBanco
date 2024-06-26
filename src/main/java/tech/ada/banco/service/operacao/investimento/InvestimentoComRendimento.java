package tech.ada.banco.service.operacao.investimento;

import java.math.BigDecimal;

import org.springframework.context.ApplicationEventPublisher;
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

	private final ApplicationEventPublisher eventPublisher;

	@Override
	public void investir(BigDecimal valor, ContaInvestimento conta) throws SaldoIndisponivelException {
		BigDecimal saldoInicial = conta.getSaldo();
		
		if (saldoInicial.compareTo(valor)<0)
			throw new SaldoIndisponivelException("Valor a ser investido ultrapassa o saldo disponível da conta investimento.");
		
		conta.setSaldo(saldoInicial.subtract(valor));
		conta.setInvestimento(conta.getInvestimento().add(valor).add(conta.getRentabilidade().calcular(valor)));
		eventPublisher.publishEvent(new InvestimentoEvent(this, conta, valor));
		repository.save(conta);
	}

	@Override
	public void resgatar(BigDecimal valor, ContaInvestimento conta) throws SaldoIndisponivelException {
		BigDecimal valorInvestido = conta.getInvestimento();
		
		if (valorInvestido.compareTo(valor)<0)
			throw new SaldoIndisponivelException("Valor do resgate superior ao valor investido.");
		
		conta.setInvestimento(valorInvestido.subtract(valor));
		conta.setSaldo(conta.getSaldo().add(valor));
		
		repository.save(conta);
	}
	
}
