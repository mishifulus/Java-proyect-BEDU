package org.bedu.postwork.controllers.mappers;

import org.bedu.postwork.model.ProductoModel;
import org.bedu.postwork.persistance.entities.Producto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoModel productoRepositoryToProductoModel (Producto productoRepository);

    Producto productoModelToProductoRepository (ProductoModel productoModel);
}
