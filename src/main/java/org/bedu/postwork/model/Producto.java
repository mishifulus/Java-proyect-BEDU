package org.bedu.postwork.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
public class Producto {

    @PositiveOrZero(message = "El identificador del producto no puede ser un número negativo")
    private long id;

    @NotEmpty(message = "El nombre del producto no puede estar en blanco.")
    @Size(min = 4, max = 30, message = "El nombre del producto debe tener entre 4 y 30 letras.")
    private String nombre;

    @NotEmpty(message = "La categoria del producto no puede estar en blanco.")
    private String categoria;

    @DecimalMin(value = "1.00", inclusive = true, message = "El precio del producto debe ser de al menos 1.00")
    @NotEmpty(message = "El precio del producto no puede estar en blanco.")
    private float precio;

    @NotEmpty(message = "El núemero de registro del producto no puede estar en blanco.")
    @Pattern(regexp = "^(\\d{3}[-]?){2}\\d{4}$")
    private String numeroRegistro;

    @PastOrPresent(message = "La fecha de creación del producto no puede ocurrir en el futuro.")
    @NotEmpty(message = "La fecha de creacion del producto no puede estar en blanco.")
    private LocalDate fechaCreacion;
}
