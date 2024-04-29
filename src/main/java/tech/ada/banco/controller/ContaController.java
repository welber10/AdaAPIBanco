package tech.ada.banco.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tech.ada.banco.dto.ContaDTO;
import tech.ada.banco.service.ContaService;

@RestController
@RequestMapping("contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService service;
    private final ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    public List<ContaDTO> listar() {
        return this.service.listar().stream().collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    public ResponseEntity<ContaDTO> buscarPorId(@PathVariable("id") Long id) {
        var cliente = this.service.findById(id);
        return cliente.map(contaDTO -> ResponseEntity.ok(this.modelMapper.map(contaDTO, ContaDTO.class)))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    public ResponseEntity excluir (@PathVariable("id") Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    public ResponseEntity<ContaDTO> inserir (@Valid @RequestBody ContaDTO dto) {
        return new ResponseEntity<>(this.service.salvar(dto), HttpStatus.CREATED);
    }

}
