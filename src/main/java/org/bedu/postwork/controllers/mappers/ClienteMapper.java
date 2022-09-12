package org.bedu.postwork.controllers.mappers;

import org.bedu.postwork.model.ClienteModel;
import org.bedu.postwork.persistance.entities.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteModel clienteRepositoryToClienteModel(Cliente clienteRepository);

    Cliente clienteModelToClienteRepository(ClienteModel clienteModel);
}
