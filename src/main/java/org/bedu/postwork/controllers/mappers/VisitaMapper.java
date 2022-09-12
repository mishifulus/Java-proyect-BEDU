package org.bedu.postwork.controllers.mappers;

import org.bedu.postwork.model.Visita;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitaMapper {

    Visita visitaRepositoryToVisitaModel (org.bedu.postwork.persistance.entities.Visita visitaRepository);

    org.bedu.postwork.persistance.entities.Visita visitaModelToVisitaRepository (Visita visitaModel);
}
