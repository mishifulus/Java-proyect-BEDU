package org.bedu.postwork.services;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.controllers.mappers.VentaMapper;
import org.bedu.postwork.model.VentaModel;
import org.bedu.postwork.persistance.VentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository repository;
    private final VentaMapper mapper;

    public VentaModel guardarVenta(VentaModel ventaModel)
    {
        return mapper.ventaRepositoryToVentaModel(
                repository.save(mapper.ventaModelToVentaRepository(ventaModel))
        );
    }

    public List<VentaModel> listarVentas()
    {
        return repository.findAll().stream().map(venta ->
                mapper.ventaRepositoryToVentaModel(venta)).collect(Collectors.toList());
    }

    public Optional<VentaModel> obtenerVenta(long idVenta)
    {
        return  repository.findById(idVenta)
                .map(venta -> Optional.of(mapper.ventaRepositoryToVentaModel(venta)))
                .orElse(Optional.empty());
    }

    public void eliminarVenta(long idVenta)
    {
        repository.deleteById(idVenta);
    }

    public VentaModel actualizarVenta(VentaModel ventaModel)
    {
        return mapper.ventaRepositoryToVentaModel(
                repository.save(mapper.ventaModelToVentaRepository(ventaModel)));
    }

    public long contarVentas()
    {
        return repository.count();
    }
}
