package org.bedu.postwork.runners;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.persistance.entities.Etapa;
import org.bedu.postwork.persistance.EtapaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EtapasVentaRunner implements CommandLineRunner {

    private final EtapaRepository etapaRepository;

    @Override
    public void run(String... args) throws Exception {
        Etapa etapa1 = creaEtapa("En espera", 0);
        Etapa etapa2 = creaEtapa("Reunión de exploración", 1);
        Etapa etapa3 = creaEtapa("Metas establecidas", 2);
        Etapa etapa4 = creaEtapa("Plan de acción presentado.", 3);
        Etapa etapa5 = creaEtapa("Contrato firmado", 4);
        Etapa etapa6 = creaEtapa("Venta ganada", 5);
        Etapa etapa7 = creaEtapa("Venta perdida", 6);

        List<Etapa> etapas = Arrays.asList(etapa1, etapa2, etapa3, etapa4, etapa5, etapa6, etapa7);
        etapaRepository.saveAll(etapas);
    }

    private Etapa creaEtapa(String nombre, Integer orden) {
        Etapa etapa = new Etapa();
        etapa.setNombre(nombre);
        etapa.setOrden(orden);

        return etapa;
    }
}
