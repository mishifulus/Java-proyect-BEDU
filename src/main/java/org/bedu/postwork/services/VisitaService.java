package org.bedu.postwork.services;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.controllers.mappers.VisitaMapper;
import org.bedu.postwork.model.Visita;
import org.bedu.postwork.persistance.VisitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitaService {

    private final VisitaRepository repository;
    private final VisitaMapper mapper;

    public Visita guardarVisita(Visita visita)
    {
        return mapper.visitaRepositoryToVisitaModel(
                repository.save(mapper.visitaModelToVisitaRepository(visita))
        );
    }

    public List<Visita> listarVisitas()
    {
        return repository.findAll().stream().map(visita -> mapper.visitaRepositoryToVisitaModel(visita)).collect(Collectors.toList());
    }

    public Optional<Visita> obtenerVisita(long idVisita)
    {
        return  repository.findById(idVisita)
                .map(visita -> Optional.of(mapper.visitaRepositoryToVisitaModel(visita)))
                .orElse(Optional.empty());
    }

    public void eliminarVisita(long idVisita)
    {
        repository.deleteById(idVisita);
    }

    public Visita actualizarVisita(Visita visita)
    {
        return mapper.visitaRepositoryToVisitaModel(repository.save(mapper.visitaModelToVisitaRepository(visita)));
    }

    public long contarVisitas()
    {
        return repository.count();
    }
}
