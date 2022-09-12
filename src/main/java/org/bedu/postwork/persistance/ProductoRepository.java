package org.bedu.postwork.persistance;

import org.bedu.postwork.persistance.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
