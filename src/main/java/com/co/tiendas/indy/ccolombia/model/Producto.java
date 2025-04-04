package com.co.tiendas.indy.ccolombia.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private double precio;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private UserEntity vendedor;

    @ManyToOne
    @JoinColumn(name = "detalle_producto_id", nullable = false)
    private DetalleProducto detalleProducto; // Es obligatorio
}
