package org.bedu.postwork.controllers.mappers;

import org.bedu.postwork.model.Etapa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EtapaMapper {

    Etapa etapaRepositoryToEtapaModel (org.bedu.postwork.persistance.entities.Etapa etapaRepository);

    org.bedu.postwork.persistance.entities.Etapa etapaModelToEtapaRepository (Etapa etapaModel);
}
