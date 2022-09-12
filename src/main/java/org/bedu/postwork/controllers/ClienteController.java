package org.bedu.postwork.controllers;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.model.ClienteModel;
import org.bedu.postwork.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteModel> getCliente(@PathVariable Long clienteId)
    {
        Optional<ClienteModel> clienteDb = clienteService.obtenerCliente(clienteId);

        if (clienteDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El cliente especificado no existe.");
        }

        return ResponseEntity.ok(clienteDb.get());
    }

    @GetMapping
    public  ResponseEntity<List<ClienteModel>> getClientes()
    {
        List<ClienteModel> clientesDb = clienteService.listarClientes();

        if(clientesDb.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existen clientes en el registro.");
        }
        return ResponseEntity.ok(clientesDb);
    }

    @PostMapping
    public ResponseEntity<Void> crearCliente(@Valid @RequestBody ClienteModel clienteModel)
    {
        ClienteModel clienteModelNuevo = clienteService.guardarCliente(clienteModel);

        return ResponseEntity.created(URI.create(String.valueOf(clienteModelNuevo.getId()))).build();
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<Void> actualizarCliente(@PathVariable Long clienteId, @Valid @RequestBody ClienteModel clienteModel)
    {
        if(clienteService.obtenerCliente(clienteId).get() == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El cliente especificado no existe.");
        }
        else
        {
            clienteModel.setId(clienteId);
            clienteService.actualizarCliente(clienteModel);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @DeleteMapping("/{clienteId}")
    public  ResponseEntity<Void> eliminarCliente(@PathVariable Long clienteId)
    {
        if(clienteService.obtenerCliente(clienteId).get() == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El cliente especificado no existe.");
        }
        else
        {
            clienteService.eliminarCliente(clienteId);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @GetMapping("/numClientes")
    public ResponseEntity<Long> getNumClientes()
    {
        long numClientes = clienteService.contarClientes();

        return ResponseEntity.ok(numClientes);
    }
}
