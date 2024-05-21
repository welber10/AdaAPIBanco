package tech.ada.banco.service.operacao.transferencia;

import java.math.BigDecimal;

import tech.ada.banco.exception.SaldoIndisponivelException;
import tech.ada.banco.exception.ValorInvalidoException;
import tech.ada.banco.model.Conta;
import tech.ada.banco.service.operacao.OperacaoBancaria;

public interface Transferencia<T extends Conta> extends OperacaoBancaria {
	
	void transferir(BigDecimal valor, T contaOrigem, T contaDestino) throws SaldoIndisponivelException, ValorInvalidoException;

}
