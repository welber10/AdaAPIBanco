package tech.ada.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.ada.banco.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
