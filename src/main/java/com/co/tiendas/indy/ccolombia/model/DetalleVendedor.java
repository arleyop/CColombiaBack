package com.co.tiendas.indy.ccolombia.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "detalle_vendedor")
public class DetalleVendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private UserEntity vendedor;

    private String cedula;
    private String nameTienda;
    private String nit;
    private String local;
    private String location;
    private String scheduleStart;
    private String scheduleEnd;
    private String contact;

    private boolean tiendaAprobada = false; // Solo un ADMIN puede aprobarla
}

