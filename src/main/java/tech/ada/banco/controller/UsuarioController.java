package tech.ada.banco.controller;


import lombok.RequiredArgsConstructor;
import tech.ada.banco.dto.UsuarioDTO;
import tech.ada.banco.service.UsuarioService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    public List<UsuarioDTO> listarTodos() {
        return this.service.listarUsuarios().stream()
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable("id") Long id) {
        var usuario = this.service.findById(id);
        return usuario.map(usuarioDTO -> ResponseEntity.ok(this.modelMapper.map(usuarioDTO, UsuarioDTO.class)))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    public ResponseEntity excluir (@PathVariable("id") Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    public ResponseEntity<UsuarioDTO> atualizar (@PathVariable("id") Long id, @RequestBody UsuarioDTO dto) {
        return new ResponseEntity<>(this.service.atualizar(id, dto), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    public ResponseEntity<UsuarioDTO> inserir (@Valid @RequestBody UsuarioDTO dto) {
        return new ResponseEntity<>(this.service.salvar(dto), HttpStatus.CREATED);
    }

}
