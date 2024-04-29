package tech.ada.banco.service.operacao.deposito;

import java.math.BigDecimal;

import tech.ada.banco.exception.ValorInvalidoException;
import tech.ada.banco.model.Conta;
import tech.ada.banco.service.operacao.OperacaoBancaria;


public interface Deposito extends OperacaoBancaria {
	
	void depositar(BigDecimal valor, Conta conta) throws ValorInvalidoException;

}
