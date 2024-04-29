package tech.ada.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.ada.banco.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByUsername(String username);
}
