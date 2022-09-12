package org.bedu.postwork.persistance;

import org.bedu.postwork.persistance.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
