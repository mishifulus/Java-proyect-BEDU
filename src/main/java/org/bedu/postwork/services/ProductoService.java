package org.bedu.postwork.services;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.controllers.mappers.ProductoMapper;
import org.bedu.postwork.model.Producto;
import org.bedu.postwork.persistance.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository repository;
    private final ProductoMapper mapper;

    public Producto guardarProducto(Producto producto)
    {
        return mapper.productoRepositoryToProductoModel(
                repository.save(mapper.productoModelToProductoRepository(producto))
        );
    }

    public List<Producto> listarProductos()
    {
        return repository.findAll().stream().map(producto -> mapper.productoRepositoryToProductoModel(producto)).collect(Collectors.toList());
    }

    public Optional<Producto> obtenerProducto(long idProducto)
    {
        return  repository.findById(idProducto)
                .map(producto -> Optional.of(mapper.productoRepositoryToProductoModel(producto)))
                .orElse(Optional.empty());
    }

    public void eliminarProducto(long idProducto)
    {
        repository.deleteById(idProducto);
    }

    public Producto actualizarProducto(Producto producto)
    {
        return mapper.productoRepositoryToProductoModel(repository.save(mapper.productoModelToProductoRepository(producto)));
    }

    public long contarProductos()
    {
        return repository.count();
    }
}
