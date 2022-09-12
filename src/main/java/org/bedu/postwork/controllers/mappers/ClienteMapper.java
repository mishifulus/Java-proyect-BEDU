package org.bedu.postwork.controllers.mappers;

import org.bedu.postwork.model.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente clienteRepositoryToClienteModel(org.bedu.postwork.persistance.entities.Cliente clienteRepository);

    org.bedu.postwork.persistance.entities.Cliente clienteModelToClienteRepository(Cliente clienteModel);
}
