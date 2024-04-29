package tech.ada.banco.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import tech.ada.banco.model.Conta;

@Transactional(readOnly = false)
public interface ContaRepository extends JpaRepository<Conta, Long> {

    @Modifying
	@Query("update Conta c set c.saldo = c.saldo - ?1 where c.id = ?2")
	void sacar(BigDecimal valor, Long id);

	@Modifying
	@Query("update Conta c set c.saldo = c.saldo + ?1 where c.id = ?2")
    void depositar(BigDecimal valor, Long id);

}
