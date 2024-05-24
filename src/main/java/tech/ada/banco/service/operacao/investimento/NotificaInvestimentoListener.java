package tech.ada.banco.service.operacao.investimento;

import java.math.BigDecimal;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.java.Log;
import tech.ada.banco.model.Conta;

@Component
@Log
public class NotificaInvestimentoListener {

    @EventListener
    public void handleInvestimentoEvent(InvestimentoEvent event) {
        Conta conta = event.getConta();
        BigDecimal valor = event.getValorInvestido();

        log.info("Investimento no valor de " + valor.doubleValue() + " na conta investimento " + conta.getId());
        log.info("Saldo pos investimento " + conta.getSaldo());
    }

}
