package org.bedu.postwork.persistance.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Table(name = "CLIENTES")
@Entity
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "correo_contacto", nullable = false)
    private String correoContacto;

    @Column(name = "numero_empleados", nullable = false)
    private String numeroEmpleados;

    @Column(nullable = false)
    private String direccion;
}
