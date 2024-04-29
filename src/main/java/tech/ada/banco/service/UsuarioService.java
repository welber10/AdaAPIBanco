package tech.ada.banco.service;

import lombok.RequiredArgsConstructor;
import tech.ada.banco.dto.UsuarioDTO;
import tech.ada.banco.exception.NaoEncontradoException;
import tech.ada.banco.model.Usuario;
import tech.ada.banco.repository.UsuarioRepository;

import org.modelmapper.ModelMapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    private UsuarioDTO convertDto(Usuario usuario) {
        return this.modelMapper.map(usuario, UsuarioDTO.class);
    }

    private Usuario convertFromDto(UsuarioDTO usuarioDto) {
        return this.modelMapper.map(usuarioDto, Usuario.class);
    }

    public Optional<UsuarioDTO> findById(Long id) {
        return this.usuarioRepository.findById(id).map(this::convertDto);
    }

    public List<UsuarioDTO> listarUsuarios() {
        return this.usuarioRepository.findAll().stream()
                .map(this::convertDto)
                .collect(Collectors.toList());
    }

    public UsuarioDTO salvar(UsuarioDTO usuarioDto) {
        var usuario = this.convertFromDto(usuarioDto);
        usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        usuario.setActive(true);
        var savedUsuario = this.usuarioRepository.save(usuario);
        return this.convertDto(savedUsuario);
    }

    public Optional<UsuarioDTO> buscarPorCpf(String cpf) {
        return this.usuarioRepository.findByCpf(cpf).map(this::convertDto);
    }

    public void excluir(Long id) {
        var usuario = this.usuarioRepository.findById(id).orElseThrow();
        this.usuarioRepository.delete(usuario);
    }

    public UsuarioDTO atualizar(Long id, UsuarioDTO usuarioDto) {
        var usuario = this.usuarioRepository.findById(id).orElseThrow();
        usuario.setNome(usuarioDto.getNome());
        usuario.setCpf(usuarioDto.getCpf());
        return this.convertDto(usuarioRepository.save(usuario));
    }
    public Usuario getByUsernameEntity(String username) {
        return this.usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado"));
    }

}
