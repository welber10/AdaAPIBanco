package tech.ada.banco.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.ada.banco.dto.ClienteDTO;
import tech.ada.banco.model.Cliente;
import tech.ada.banco.repository.ClienteRepository;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    private final ModelMapper modelMapper;

    public Optional<ClienteDTO> findById(Long id) {
        return this.clienteRepository.findById(id).map(this::converDto);
    }

    public ClienteDTO salvar(ClienteDTO clienteDTO) {
        var cliente = this.convertFromDto(clienteDTO);
        cliente.setId(cliente.getId());
        var savedCliente = this.clienteRepository.save(cliente);
        return this.converDto(savedCliente);
    }

    public ClienteDTO atualizar(Long id, ClienteDTO clienteDTO) {
        var cliente = this.clienteRepository.findById(id).orElseThrow();
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setNome(clienteDTO.getNome());
        cliente.setDataNascimento(clienteDTO.getDataNascimento());
        return this.converDto(clienteRepository.save(cliente));
    }

    public void excluir(Long id) {
        var cliente = this.clienteRepository.findById(id).orElseThrow();
        this.clienteRepository.delete(cliente);
    }

    public List<ClienteDTO> listar() {
        return this.clienteRepository.findAll().stream()
                    .map(this::converDto)
                    .collect(Collectors.toList());
    }

    private ClienteDTO converDto(Cliente cliente) {
        return this.modelMapper.map(cliente, ClienteDTO.class);
    }

    private Cliente convertFromDto(ClienteDTO clienteDTO) {
        return this.modelMapper.map(clienteDTO, Cliente.class);
    }

}
