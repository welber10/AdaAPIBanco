package tech.ada.banco.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tech.ada.banco.dto.ClienteDTO;
import tech.ada.banco.service.ClienteService;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;
    private final ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    public List<ClienteDTO> listar() {
        return this.service.listar().stream().collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable("id") Long id) {
        var cliente = this.service.findById(id);
        return cliente.map(clienteDTO -> ResponseEntity.ok(this.modelMapper.map(clienteDTO, ClienteDTO.class)))
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
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable("id") Long id, @RequestBody ClienteDTO dto) {
        return new ResponseEntity<>(this.service.atualizar(id, dto), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    public ResponseEntity<ClienteDTO> inserir (@Valid @RequestBody ClienteDTO dto) {
        return new ResponseEntity<>(this.service.salvar(dto), HttpStatus.CREATED);
    }

}
