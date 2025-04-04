package com.co.tiendas.indy.ccolombia.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "detalle_usuario")
public class DetalleUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserEntity usuario;

    private String correo;
    private String telefono;
    private String localidad;
    private String barrio;
    private String direccion;
}
