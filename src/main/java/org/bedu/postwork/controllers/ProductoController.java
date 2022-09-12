package org.bedu.postwork.controllers;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.model.Cliente;
import org.bedu.postwork.model.Producto;
import org.bedu.postwork.services.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping("/{productoId}")
    public ResponseEntity<Producto> getProducto(@PathVariable Long productoId)
    {
        Optional<Producto> productoDb = productoService.obtenerProducto(productoId);

        if (productoDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto especificado no existe.");
        }

        return ResponseEntity.ok(productoDb.get());
    }

    @GetMapping
    public  ResponseEntity<List<Producto>> getProductos()
    {
        List<Producto> productosDb = productoService.listarProductos();

        if(productosDb.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existen productos en el registro.");
        }
        return ResponseEntity.ok(productosDb);
    }

    @PostMapping
    public ResponseEntity<Void> crearProducto(@RequestBody Producto producto)
    {
        Producto productoNuevo = productoService.guardarProducto(producto);

        return ResponseEntity.created(URI.create(String.valueOf(productoNuevo.getId()))).build();
    }

    @PutMapping("/{productoId}")
    public ResponseEntity<Void> actualizarProducto(@PathVariable Long productoId, @RequestBody Producto producto)
    {
        productoService.actualizarProducto(producto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{productoId}")
    public  ResponseEntity<Void> eliminarProducto(@PathVariable Long productoId)
    {
        productoService.eliminarProducto(productoId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/numProductos")
    public ResponseEntity<Long> getNumProductos()
    {
        long numProductos = productoService.contarProductos();

        return ResponseEntity.ok(numProductos);
    }
}
