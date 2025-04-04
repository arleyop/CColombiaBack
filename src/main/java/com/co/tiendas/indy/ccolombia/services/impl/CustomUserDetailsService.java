package com.co.tiendas.indy.ccolombia.services.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.co.tiendas.indy.ccolombia.model.RolEntity;
import com.co.tiendas.indy.ccolombia.model.UserEntity;
import com.co.tiendas.indy.ccolombia.persisten.RolRepository;
import com.co.tiendas.indy.ccolombia.persisten.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserRepository userRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRol().getNombre())  // Asigna el rol
                .build();
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
