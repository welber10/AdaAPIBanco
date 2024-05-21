package tech.ada.banco.service.operacao.saque;

import java.math.BigDecimal;

import tech.ada.banco.exception.SaldoIndisponivelException;
import tech.ada.banco.exception.ValorInvalidoException;
import tech.ada.banco.model.Conta;
import tech.ada.banco.service.operacao.OperacaoBancaria;

public interface Saque<T extends Conta> extends OperacaoBancaria {
	
	void sacar(BigDecimal valor, T conta) throws SaldoIndisponivelException, ValorInvalidoException;

}
