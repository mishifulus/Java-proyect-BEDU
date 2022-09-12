package org.bedu.postwork.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class VentaModel {

    @PositiveOrZero(message = "El identificador de la venta no puede ser un número negativo")
    private long ventaId;

    @DecimalMin(value = "1.00", inclusive = true, message = "La venta debe ser de al menos 1.00")
    //@NotEmpty(message = "La venta debe tener un monto.")
    private float monto;

    @NotEmpty(message = "La venta debe tener por lo menos un producto.")
    private List<ProductoModel> productoModels;

    @NotNull(message = "La venta debe haberse realizado a algún cliente.")
    private ClienteModel clienteModel;

    @PastOrPresent(message = "La venta no puede ocurrir en el futuro.")
    //@NotEmpty(message = "La venta debe tener fecha de creacion.")
    private LocalDateTime fechaCreacion;
}
