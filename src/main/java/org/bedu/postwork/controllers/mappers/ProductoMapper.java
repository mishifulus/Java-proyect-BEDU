package org.bedu.postwork.controllers.mappers;

import org.bedu.postwork.model.Producto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    Producto productoRepositoryToProductoModel (org.bedu.postwork.persistance.entities.Producto productoRepository);

    org.bedu.postwork.persistance.entities.Producto productoModelToProductoRepository (Producto productoModel);
}
