package tech.ada.banco.service.operacao.transferencia;

import java.math.BigDecimal;

import tech.ada.banco.exception.SaldoIndisponivelException;
import tech.ada.banco.exception.ValorInvalidoException;
import tech.ada.banco.model.Conta;
import tech.ada.banco.service.operacao.OperacaoBancaria;

public interface Transferencia extends OperacaoBancaria {
	
	void transferir(BigDecimal valor, Conta contaOrigem, Conta contaDestino) throws SaldoIndisponivelException, ValorInvalidoException;

}
