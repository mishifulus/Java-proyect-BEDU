package org.bedu.postwork.controllers;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.model.VentaModel;
import org.bedu.postwork.services.VentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/venta")
@RequiredArgsConstructor
public class VentaController{

    private final VentaService ventaService;

    @GetMapping("/{ventaId}")
    public ResponseEntity<VentaModel> getVenta(@PathVariable Long ventaId)
    {
        Optional<VentaModel> ventaDb = ventaService.obtenerVenta(ventaId);

        if (ventaDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La venta especificada no existe.");
        }

        return ResponseEntity.ok(ventaDb.get());
    }

    @GetMapping
    public  ResponseEntity<List<VentaModel>> getVentas()
    {
        List<VentaModel> ventasDb = ventaService.listarVentas();

        if(ventasDb.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existen ventas en el registro.");
        }
        return ResponseEntity.ok(ventasDb);
    }

    @PostMapping
    public ResponseEntity<Void> crearVenta(@Valid @RequestBody VentaModel ventaModel)
    {
        VentaModel ventaModelNueva = ventaService.guardarVenta(ventaModel);

        return ResponseEntity.created(URI.create(String.valueOf(ventaModelNueva.getVentaId()))).build();
    }

    @PutMapping("/{ventaId}")
    public ResponseEntity<Void> actualizarVenta(@PathVariable Long ventaId, @Valid @RequestBody VentaModel ventaModel)
    {
        if(ventaService.obtenerVenta(ventaId).get() == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La venta especificada no existe.");
        }
        else
        {
            ventaModel.setVentaId(ventaId);
            ventaService.actualizarVenta(ventaModel);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @DeleteMapping("/{ventaId}")
    public  ResponseEntity<Void> eliminarVenta(@PathVariable Long ventaId)
    {
        if(ventaService.obtenerVenta(ventaId).get() == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La venta especificada no existe.");
        }
        else
        {
            ventaService.eliminarVenta(ventaId);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @GetMapping("/numVentas")
    public ResponseEntity<Long> getNumVentas()
    {
        long numVentas = ventaService.contarVentas();

        return ResponseEntity.ok(numVentas);
    }
}
