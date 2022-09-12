package org.bedu.postwork.controllers;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.model.VisitaModel;
import org.bedu.postwork.services.VisitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/visita")
@RequiredArgsConstructor
public class VisitaController {

    private final VisitaService visitaService;

    @GetMapping("/{visitaId}")
    public ResponseEntity<VisitaModel> getVisita(@PathVariable Long visitaId)
    {
        Optional<VisitaModel> visitaDb = visitaService.obtenerVisita(visitaId);

        if (visitaDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La visita especificada no existe.");
        }

        return ResponseEntity.ok(visitaDb.get());
    }

    @GetMapping
    public  ResponseEntity<List<VisitaModel>> getVisitas()
    {
        List<VisitaModel> visitasDb = visitaService.listarVisitas();

        if(visitasDb.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existen visitas en el registro.");
        }
        return ResponseEntity.ok(visitasDb);
    }

    @PostMapping
    public ResponseEntity<Void> crearVisita(@RequestBody VisitaModel visitaModel)
    {
        VisitaModel visitaModelNueva = visitaService.guardarVisita(visitaModel);

        return ResponseEntity.created(URI.create(String.valueOf(visitaModelNueva.getId()))).build();
    }

    @PutMapping("/{visitaId}")
    public ResponseEntity<Void> actualizarVisita(@PathVariable Long visitaId, @RequestBody VisitaModel visitaModel)
    {
        visitaService.actualizarVisita(visitaModel);
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
