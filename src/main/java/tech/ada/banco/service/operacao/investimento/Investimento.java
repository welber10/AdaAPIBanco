package tech.ada.banco.service.operacao.investimento;

import java.math.BigDecimal;

import tech.ada.banco.exception.SaldoIndisponivelException;
import tech.ada.banco.model.Conta;
import tech.ada.banco.service.operacao.OperacaoBancaria;

public interface Investimento <T extends Conta> extends OperacaoBancaria {
	
	void investir(BigDecimal valor, T conta) throws SaldoIndisponivelException;

}
