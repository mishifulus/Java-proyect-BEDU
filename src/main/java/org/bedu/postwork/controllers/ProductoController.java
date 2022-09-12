package org.bedu.postwork.controllers;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.model.ProductoModel;
import org.bedu.postwork.services.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping("/{productoId}")
    public ResponseEntity<ProductoModel> getProducto(@PathVariable Long productoId)
    {
        Optional<ProductoModel> productoDb = productoService.obtenerProducto(productoId);

        if (productoDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto especificado no existe.");
        }

        return ResponseEntity.ok(productoDb.get());
    }

    @GetMapping
    public  ResponseEntity<List<ProductoModel>> getProductos()
    {
        List<ProductoModel> productosDb = productoService.listarProductos();

        if(productosDb.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existen productos en el registro.");
        }
        return ResponseEntity.ok(productosDb);
    }

    @PostMapping
    public ResponseEntity<Void> crearProducto(@RequestBody ProductoModel productoModel)
    {
        ProductoModel productoModelNuevo = productoService.guardarProducto(productoModel);

        return ResponseEntity.created(URI.create(String.valueOf(productoModelNuevo.getId()))).build();
    }

    @PutMapping("/{productoId}")
    public ResponseEntity<Void> actualizarProducto(@PathVariable Long productoId, @RequestBody ProductoModel productoModel)
    {
        if(productoService.obtenerProducto(productoId).get() == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto especificado no existe.");
        }
        else
        {
            productoModel.setId(productoId);
            productoService.actualizarProducto(productoModel);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @DeleteMapping("/{productoId}")
    public  ResponseEntity<Void> eliminarProducto(@PathVariable Long productoId)
    {
        if(productoService.obtenerProducto(productoId).get() == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto especificado no existe.");
        }
        else
        {
            productoService.eliminarProducto(productoId);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @GetMapping("/numProductos")
    public ResponseEntity<Long> getNumProductos()
    {
        long numProductos = productoService.contarProductos();

        return ResponseEntity.ok(numProductos);
    }
}
