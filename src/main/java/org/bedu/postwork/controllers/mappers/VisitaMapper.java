package org.bedu.postwork.controllers.mappers;

import org.bedu.postwork.model.VisitaModel;
import org.bedu.postwork.persistance.entities.Visita;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitaMapper {

    VisitaModel visitaRepositoryToVisitaModel (Visita visitaRepository);

    Visita visitaModelToVisitaRepository (VisitaModel visitaModel);
}
