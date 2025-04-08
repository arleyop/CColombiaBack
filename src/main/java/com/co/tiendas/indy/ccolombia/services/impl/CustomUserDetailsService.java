package com.co.tiendas.indy.ccolombia.services.impl;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.co.tiendas.indy.ccolombia.dto.AuthResponse;
import com.co.tiendas.indy.ccolombia.model.RolEntity;
import com.co.tiendas.indy.ccolombia.model.UserEntity;
import com.co.tiendas.indy.ccolombia.persisten.RolRepository;
import com.co.tiendas.indy.ccolombia.persisten.UserRepository;
import com.co.tiendas.indy.ccolombia.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public CustomUserDetailsService(UserRepository userRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ✅ Este método es requerido por Spring Security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Crea un UserDetails a partir de tu entidad
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRol().getNombre()))
        );
    }

    // ✅ Este es tu método personalizado para responder con AuthResponse
    public AuthResponse loadUserAuthResponse(String username, HttpServletRequest request) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String token = jwtUtil.generateToken(user.getUsername());

        AuthResponse response = new AuthResponse();
        response.setOk(true);
        response.setPath(request.getRequestURI());
        response.setMsg("Login exitoso");
        response.setToken(token);
        response.setId(user.getId().toString());
        response.setName(user.getUsername());
        response.setRole(user.getRol().getNombre());

        return response;
    }

    public void registerUser(String username, String password, String rolNombre) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }
        RolEntity rol = rolRepository.findByNombre(rolNombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRol(rol);
        userRepository.save(newUser);
    }
}
