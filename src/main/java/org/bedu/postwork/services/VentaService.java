package org.bedu.postwork.services;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.controllers.mappers.ClienteMapper;
import org.bedu.postwork.controllers.mappers.VentaMapper;
import org.bedu.postwork.model.Cliente;
import org.bedu.postwork.model.Venta;
import org.bedu.postwork.persistance.ClienteRepository;
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

    public Venta guardarVenta(Venta venta)
    {
        return mapper.ventaRepositoryToVentaModel(
                repository.save(mapper.ventaModelToVentaRepository(venta))
        );
    }

    public List<Venta> listarVentas()
    {
        return repository.findAll().stream().map(venta -> mapper.ventaRepositoryToVentaModel(venta)).collect(Collectors.toList());
    }

    public Optional<Venta> obtenerVenta(long idVenta)
    {
        return  repository.findById(idVenta)
                .map(venta -> Optional.of(mapper.ventaRepositoryToVentaModel(venta)))
                .orElse(Optional.empty());
    }

    public void eliminarVenta(long idVenta)
    {
        repository.deleteById(idVenta);
    }

    public Venta actualizarVenta(Venta venta)
    {
        return mapper.ventaRepositoryToVentaModel(repository.save(mapper.ventaModelToVentaRepository(venta)));
    }

    public long contarVentas()
    {
        return repository.count();
    }
}
