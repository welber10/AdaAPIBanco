package tech.ada.banco.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.ada.banco.dto.ContaDTO;
import tech.ada.banco.model.Conta;
import tech.ada.banco.repository.ContaRepository;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository repository;

    private final ModelMapper modelMapper;

    public Optional<ContaDTO> findById(Long id) {
        return this.repository.findById(id).map(this::convertDto);
    }

    public ContaDTO salvar(ContaDTO contaDTO) {
        var conta = this.convertFromDto(contaDTO);
        conta.setId(conta.getId());
        var savedConta = this.repository.save(conta);
        return this.convertDto(savedConta);
    }

    public void excluir(Long id) {
        var conta = this.repository.findById(id).orElseThrow();
        this.repository.delete(conta);
    }

    public List<ContaDTO> listar() {
        return this.repository.findAll().stream()
                    .map(this::convertDto)
                    .collect(Collectors.toList());
    }

    private ContaDTO convertDto(Conta conta) {
        return this.modelMapper.map(conta, ContaDTO.class);
    }

    private Conta convertFromDto(ContaDTO contaDTO) {
        return this.modelMapper.map(contaDTO, Conta.class);
    }

}
