package tech.ada.banco.service.operacao.saldo;

import java.math.BigDecimal;

import tech.ada.banco.dto.ContaDTO;
import tech.ada.banco.service.operacao.OperacaoBancaria;


public interface Saldo extends OperacaoBancaria {
	
	BigDecimal consultar(ContaDTO conta);
}
