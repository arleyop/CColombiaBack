package com.co.tiendas.indy.ccolombia.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private String correo;
    private String telefono;
    private String localidad;
    private String barrio;
    private String direccion;
}