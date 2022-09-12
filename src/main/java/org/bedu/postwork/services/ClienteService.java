package org.bedu.postwork.services;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.controllers.mappers.ClienteMapper;
import org.bedu.postwork.model.ClienteModel;
import org.bedu.postwork.persistance.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    public ClienteModel guardarCliente(ClienteModel clienteModel)
    {
        return mapper.clienteRepositoryToClienteModel(
                repository.save(mapper.clienteModelToClienteRepository(clienteModel))
        );
    }

    public List<ClienteModel> listarClientes()
    {
        return repository.findAll().stream().map(cliente ->
                mapper.clienteRepositoryToClienteModel(cliente)).collect(Collectors.toList());
    }

    public Optional<ClienteModel> obtenerCliente(long idCliente)
    {
        return  repository.findById(idCliente)
                .map(cliente -> Optional.of(mapper.clienteRepositoryToClienteModel(cliente)))
                .orElse(Optional.empty());
    }

    public void eliminarCliente(long idCliente)
    {
        repository.deleteById(idCliente);
    }

    public ClienteModel actualizarCliente(ClienteModel clienteModel)
    {
        return mapper.clienteRepositoryToClienteModel(
                repository.save(mapper.clienteModelToClienteRepository(clienteModel)));
    }

    public long contarClientes()
    {
        return repository.count();
    }
}
