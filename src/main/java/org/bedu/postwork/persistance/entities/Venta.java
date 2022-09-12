package org.bedu.postwork.persistance.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bedu.postwork.model.Cliente;
import org.bedu.postwork.model.Producto;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Table(name = "VENTA")
@Entity
@NoArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ventaId;

    @Column(nullable = false)
    private float monto;

    //@OneToMany
    //@JoinColumn(name = "productos_fk", referencedColumnName = "id")
    @ElementCollection
    @CollectionTable(name = "comanda", joinColumns = {@JoinColumn(name = "venta_fk", referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "producto_fk", referencedColumnName = "id")
    @Column(name = "productos")
    private List<Producto> productos;

    @ManyToOne
    @JoinColumn(name = "cliente_fk", referencedColumnName = "id")
    private Cliente cliente;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
}
