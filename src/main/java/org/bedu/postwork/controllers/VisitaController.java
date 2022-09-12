package org.bedu.postwork.controllers;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.model.Cliente;
import org.bedu.postwork.model.Visita;
import org.bedu.postwork.services.VisitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/visita")
@RequiredArgsConstructor
public class VisitaController {

    private final VisitaService visitaService;

    @GetMapping("/{visitaId}")
    public ResponseEntity<Visita> getVisita(@PathVariable Long visitaId)
    {
        Optional<Visita> visitaDb = visitaService.obtenerVisita(visitaId);

        if (visitaDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La visita especificada no existe.");
        }

        return ResponseEntity.ok(visitaDb.get());
    }

    @GetMapping
    public  ResponseEntity<List<Visita>> getVisitas()
    {
        List<Visita> visitasDb = visitaService.listarVisitas();

        if(visitasDb.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existen visitas en el registro.");
        }
        return ResponseEntity.ok(visitasDb);
    }

    @PostMapping
    public ResponseEntity<Void> crearVisita(@RequestBody Visita visita)
    {
        Visita visitaNueva = visitaService.guardarVisita(visita);

        return ResponseEntity.created(URI.create(String.valueOf(visitaNueva.getId()))).build();
    }

    @PutMapping("/{visitaId}")
    public ResponseEntity<Void> actualizarVisita(@PathVariable Long visitaId, @RequestBody Visita visita)
    {
        visitaService.actualizarVisita(visita);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{visitaId}")
    public  ResponseEntity<Void> eliminarVisita(@PathVariable Long visitaId)
    {
        visitaService.eliminarVisita(visitaId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/numVisitas")
    public ResponseEntity<Long> getNumVisitas()
    {
        long numVisitas = visitaService.contarVisitas();

        return ResponseEntity.ok(numVisitas);
    }
}
