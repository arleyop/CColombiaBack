package com.co.tiendas.indy.ccolombia.services.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.co.tiendas.indy.ccolombia.dto.UsuarioDTO;
import com.co.tiendas.indy.ccolombia.dto.VendedorDTO;
import com.co.tiendas.indy.ccolombia.model.DetalleUsuario;
import com.co.tiendas.indy.ccolombia.model.DetalleVendedor;
import com.co.tiendas.indy.ccolombia.model.RolEntity;
import com.co.tiendas.indy.ccolombia.model.UserEntity;
import com.co.tiendas.indy.ccolombia.persisten.DetalleUsuarioRepository;
import com.co.tiendas.indy.ccolombia.persisten.DetalleVendedorRepository;
import com.co.tiendas.indy.ccolombia.persisten.RolRepository;
import com.co.tiendas.indy.ccolombia.persisten.UserRepository;
import com.co.tiendas.indy.ccolombia.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final DetalleUsuarioRepository detalleUsuarioRepository;
    private final DetalleVendedorRepository detalleVendedorRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RolRepository rolRepository, 
                           DetalleUsuarioRepository detalleUsuarioRepository,
                           DetalleVendedorRepository detalleVendedorRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.detalleUsuarioRepository = detalleUsuarioRepository;
        this.detalleVendedorRepository = detalleVendedorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void completarDetalleUsuario(String username, UsuarioDTO usuarioDTO) {
        UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        DetalleUsuario detalle = new DetalleUsuario();
        detalle.setUsuario(user);
        detalle.setCorreo(usuarioDTO.getCorreo());
        detalle.setTelefono(usuarioDTO.getTelefono());
        detalle.setLocalidad(usuarioDTO.getLocalidad());
        detalle.setBarrio(usuarioDTO.getBarrio());
        detalle.setDireccion(usuarioDTO.getDireccion());

        detalleUsuarioRepository.save(detalle);
    }

    @Transactional
    public void completarDetalleVendedor(String username, VendedorDTO vendedorDTO) {
        UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        DetalleVendedor detalle = new DetalleVendedor();
        detalle.setVendedor(user);
        detalle.setCedula(vendedorDTO.getCedula());
        detalle.setNameTienda(vendedorDTO.getNameTienda());
        detalle.setNit(vendedorDTO.getNit());
        detalle.setLocal(vendedorDTO.getLocal());
        detalle.setLocation(vendedorDTO.getLocation());
        detalle.setScheduleStart(vendedorDTO.getScheduleStart());
        detalle.setScheduleEnd(vendedorDTO.getScheduleEnd());
        detalle.setContact(vendedorDTO.getContact());

        detalleVendedorRepository.save(detalle);

        // Cambiar rol de usuario a "VENDEDOR"
        RolEntity rolVendedor = rolRepository.findByNombre("VENDEDOR")
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        user.setRol(rolVendedor);
        userRepository.save(user);
    }


    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
