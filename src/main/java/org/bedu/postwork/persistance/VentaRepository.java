package org.bedu.postwork.persistance;

import org.bedu.postwork.persistance.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}
