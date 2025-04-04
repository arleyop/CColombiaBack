package com.co.tiendas.indy.ccolombia.services;

import java.util.List;

import com.co.tiendas.indy.ccolombia.dto.UsuarioDTO;
import com.co.tiendas.indy.ccolombia.dto.VendedorDTO;
import com.co.tiendas.indy.ccolombia.model.UserEntity;

public interface UserService {
    List<UserEntity> getAllUsers();
    void completarDetalleUsuario(String username, UsuarioDTO usuarioDTO);
    void completarDetalleVendedor(String username, VendedorDTO vendedorDTO);
}

