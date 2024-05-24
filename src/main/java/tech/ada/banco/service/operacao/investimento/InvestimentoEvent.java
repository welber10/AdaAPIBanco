package tech.ada.banco.service.operacao.investimento;

import java.math.BigDecimal;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import tech.ada.banco.model.Conta;

@Getter
public class InvestimentoEvent extends ApplicationEvent {

    private final Conta conta;
    private final BigDecimal valorInvestido; 

    public InvestimentoEvent(Object source, Conta conta, BigDecimal valorInvestido) {
        super(source);
        this.conta = conta;
        this.valorInvestido = valorInvestido;
    }

}
