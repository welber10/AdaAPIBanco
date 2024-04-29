package tech.ada.banco.controller;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tech.ada.banco.exception.SaldoIndisponivelException;
import tech.ada.banco.exception.ValorInvalidoException;
import tech.ada.banco.model.Conta;
import tech.ada.banco.model.Usuario;
import tech.ada.banco.service.ContaService;
import tech.ada.banco.service.login.JwtService;
import tech.ada.banco.service.operacao.deposito.DepositoConta;
import tech.ada.banco.service.operacao.saque.SaqueConta;
import tech.ada.banco.service.operacao.transferencia.TransferenciaBancaria;

@RestController
@RequestMapping("operacoes-bancarias")
@RequiredArgsConstructor
public class OperacoesBancariasController {

    private static final String MSG_ACESSO_NEGADO_NAO_TITULAR = "Acesso negado. Somente o titular da conta pode realizar essa operação!!!";
    
    private final ModelMapper modelMapper;
    private final ContaService contaService;
    private final SaqueConta saque;
    private final DepositoConta deposito;
    private final TransferenciaBancaria transferencia;
    private final JwtService jwtService;

    @GetMapping("/saldo/{idConta}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<BigDecimal> saldo(@PathVariable("idConta") Long idConta, @RequestHeader(name="Authorization") String bearerToken) {
        var conta = this.modelMapper.map(this.contaService.findById(idConta), Conta.class);

        if (isTitular((Usuario) jwtService.getUserDetails(bearerToken.substring(7)), conta))
            throw new AccessDeniedException(MSG_ACESSO_NEGADO_NAO_TITULAR);

        return new ResponseEntity<>(conta.getSaldo(), HttpStatus.OK);
    }

    @PutMapping("/sacar/{valor}/{idConta}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<HttpStatus> sacar(@PathVariable("valor") BigDecimal valor, @PathVariable("idConta") Long idConta, @RequestHeader(name="Authorization") String bearerToken) throws SaldoIndisponivelException, ValorInvalidoException {
        var conta = this.modelMapper.map(this.contaService.findById(idConta), Conta.class);

        if (isTitular((Usuario) jwtService.getUserDetails(bearerToken.substring(7)), conta))
            throw new AccessDeniedException(MSG_ACESSO_NEGADO_NAO_TITULAR);

        saque.sacar(valor, conta);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/depositar/{valor}/{idConta}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<HttpStatus> depositar(@PathVariable("valor") BigDecimal valor, @PathVariable("idConta") Long idConta, @RequestHeader(name="Authorization") String bearerToken) throws ValorInvalidoException {
        var conta = this.modelMapper.map(this.contaService.findById(idConta), Conta.class);

        if (isTitular((Usuario) jwtService.getUserDetails(bearerToken.substring(7)), conta))
            throw new AccessDeniedException(MSG_ACESSO_NEGADO_NAO_TITULAR);

        deposito.depositar(valor, conta);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/transferir/{valor}/{idContaOrigem}/{idContaDestino}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<HttpStatus> transferir(@PathVariable("valor") BigDecimal valor, @PathVariable("idContaOrigem") Long idContaOrigem, @PathVariable("idContaDestino") Long idContaDestino, @RequestHeader(name="Authorization") String bearerToken) throws SaldoIndisponivelException, ValorInvalidoException {
        var contaOrigem = this.modelMapper.map(this.contaService.findById(idContaOrigem), Conta.class);
        var contaDestino = this.modelMapper.map(this.contaService.findById(idContaDestino), Conta.class);
        
        if (isTitular((Usuario) jwtService.getUserDetails(bearerToken.substring(7)), contaOrigem))
            throw new AccessDeniedException(MSG_ACESSO_NEGADO_NAO_TITULAR);

        
        transferencia.transferir(valor, contaOrigem, contaDestino);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean isTitular(Usuario usuario, Conta conta) {
        return !usuario.getId().equals(conta.getCliente().getId());
    }

}
