package org.bedu.postwork.controllers.mappers;

import org.bedu.postwork.model.Venta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VentaMapper {

    Venta ventaRepositoryToVentaModel (org.bedu.postwork.persistance.entities.Venta ventaRepository);

    org.bedu.postwork.persistance.entities.Venta ventaModelToVentaRepository (Venta ventaModel);
}
