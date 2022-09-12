package org.bedu.postwork.services;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.controllers.mappers.ClienteMapper;
import org.bedu.postwork.persistance.ClienteRepository;
import org.bedu.postwork.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    public Cliente guardarCliente(Cliente cliente)
    {
        return mapper.clienteRepositoryToClienteModel(
                repository.save(mapper.clienteModelToClienteRepository(cliente))
        );
    }

    public List<Cliente> listarClientes()
    {
        return repository.findAll().stream().map(cliente -> mapper.clienteRepositoryToClienteModel(cliente)).collect(Collectors.toList());
    }

    public Optional<Cliente> obtenerCliente(long idCliente)
    {
        return  repository.findById(idCliente)
                .map(cliente -> Optional.of(mapper.clienteRepositoryToClienteModel(cliente)))
                .orElse(Optional.empty());
    }

    public void eliminarCliente(long idCliente)
    {
        repository.deleteById(idCliente);
    }

    public Cliente actualizarCliente(Cliente cliente)
    {
        return mapper.clienteRepositoryToClienteModel(repository.save(mapper.clienteModelToClienteRepository(cliente)));
    }

    public long contarClientes()
    {
        return repository.count();
    }
}
