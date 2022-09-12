package org.bedu.postwork.controllers;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.model.Etapa;
import org.bedu.postwork.services.EtapaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/etapa")
@RequiredArgsConstructor
public class EtapaController {

    private final EtapaService etapaService;

    @GetMapping("/{etapaId}")
    public ResponseEntity<Etapa> getEtapa(@PathVariable Long etapaId)
    {
        Optional<Etapa> etapaDb = etapaService.obtenerEtapa(etapaId);

        if (etapaDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La etapa especificada no existe.");
        }

        return ResponseEntity.ok(etapaDb.get());
    }

    @GetMapping
    public  ResponseEntity<List<Etapa>> getEtapas()
    {
        List<Etapa> etapasDb = etapaService.listarEtapas();

        if(etapasDb.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existen etapas en el registro.");
        }
        return ResponseEntity.ok(etapasDb);
    }

    @PostMapping
    public ResponseEntity<Void> crearEtapa(@Valid @RequestBody Etapa etapa)
    {
        Etapa etapaNuevo = etapaService.guardarEtapa(etapa);

        return ResponseEntity.created(URI.create(String.valueOf(etapaNuevo.getEtapaId()))).build();
    }

    @PutMapping("/{etapaId}")
    public ResponseEntity<Void> actualizarEtapa(@PathVariable Long etapaId, @Valid @RequestBody Etapa etapa)
    {
        etapaService.actualizarEtapa(etapa);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{etapaId}")
    public  ResponseEntity<Void> eliminarEtapa(@PathVariable Long etapaId)
    {
        etapaService.eliminarEtapa(etapaId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/numEtapas")
    public ResponseEntity<Long> getNumEtapas()
    {
        long numEtapas = etapaService.contarEtapas();

        return ResponseEntity.ok(numEtapas);
    }
}
