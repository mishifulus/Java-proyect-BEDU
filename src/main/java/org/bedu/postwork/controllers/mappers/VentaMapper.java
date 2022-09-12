package org.bedu.postwork.controllers.mappers;

import org.bedu.postwork.model.VentaModel;
import org.bedu.postwork.persistance.entities.Venta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VentaMapper {

    VentaModel ventaRepositoryToVentaModel (Venta ventaRepository);

    Venta ventaModelToVentaRepository (VentaModel ventaModel);
}
