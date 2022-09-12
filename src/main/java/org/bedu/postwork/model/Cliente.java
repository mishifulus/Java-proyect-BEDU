package org.bedu.postwork.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@RequiredArgsConstructor
public class Cliente {

    @PositiveOrZero(message = "El identificador no puede ser un número negativo")
    private long id;

    @NotEmpty(message = "El nombre del cliente no puede estar vacío")
    @Size(min = 5, max = 30, message = "El nombre del cliente debe tener al menos 5 letras y ser menor a 30")
    private String nombre;

    @Email(message = "El correo debe tener el formato correcto")
    @NotEmpty(message = "El correo del cliente no puede estar vacío")
    private String correoContacto;

    @Min(value = 10, message = "Los clientes con menos de 10 empleados no son válidos")
    @Max(value = 10000, message = "Los clientes con más de 10000 empleados no son válidos")
    @NotEmpty(message = "El numero de empleado del cliente no puede estar vacío")
    private String numeroEmpleados;

    @NotBlank(message = "Se debe proporcionar una dirección")
    private String direccion;
}
