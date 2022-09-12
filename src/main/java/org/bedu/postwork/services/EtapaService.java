package org.bedu.postwork.services;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.controllers.mappers.EtapaMapper;
import org.bedu.postwork.model.EtapaModel;
import org.bedu.postwork.persistance.EtapaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EtapaService {

    private final EtapaRepository repository;
    private final EtapaMapper mapper;

    public EtapaModel guardarEtapa(EtapaModel etapaModel)
    {
        return mapper.etapaRepositoryToEtapaModel(
                repository.save(mapper.etapaModelToEtapaRepository(etapaModel))
        );
    }

    public List<EtapaModel> listarEtapas()
    {
        return repository.findAll().stream().map(etapa ->
                mapper.etapaRepositoryToEtapaModel(etapa)).collect(Collectors.toList());
    }

    public Optional<EtapaModel> obtenerEtapa(long idEtapa)
    {
        return  repository.findById(idEtapa)
                .map(etapa -> Optional.of(mapper.etapaRepositoryToEtapaModel(etapa)))
                .orElse(Optional.empty());
    }

    public void eliminarEtapa(long idEtapa)
    {
        repository.deleteById(idEtapa);
    }

    public EtapaModel actualizarEtapa(EtapaModel etapaModel)
    {
        return mapper.etapaRepositoryToEtapaModel(
                repository.save(mapper.etapaModelToEtapaRepository(etapaModel)));
    }

    public long contarEtapas()
    {
        return repository.count();
    }
}
