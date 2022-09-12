package org.bedu.postwork.persistance.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Table(name = "PRODUCTO")
@Entity
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private float precio;

    @Column(name = "numero_registro", nullable = false, length = 20)
    private String numeroRegistro;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;
}
