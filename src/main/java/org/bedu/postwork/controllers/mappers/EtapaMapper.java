package org.bedu.postwork.controllers.mappers;

import org.bedu.postwork.model.EtapaModel;
import org.bedu.postwork.persistance.entities.Etapa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EtapaMapper {

    EtapaModel etapaRepositoryToEtapaModel (Etapa etapaRepository);

    Etapa etapaModelToEtapaRepository (EtapaModel etapaModel);
}
