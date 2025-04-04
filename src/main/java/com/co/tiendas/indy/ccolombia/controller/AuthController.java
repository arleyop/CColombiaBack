package com.co.tiendas.indy.ccolombia.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.co.tiendas.indy.ccolombia.dto.UsuarioDTO;
import com.co.tiendas.indy.ccolombia.dto.VendedorDTO;
import com.co.tiendas.indy.ccolombia.model.UserEntity;
import com.co.tiendas.indy.ccolombia.security.JwtUtil;
import com.co.tiendas.indy.ccolombia.services.UserService;
import com.co.tiendas.indy.ccolombia.services.impl.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JwtUtil jwtUtil,UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String rol) {
        userDetailsService.registerUser(username, password, rol);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtUtil.generateToken(userDetails.getUsername());
    }
    
    @PostMapping("/detalle/usuario")
    public ResponseEntity<String> completarDetalleUsuario(@RequestBody UsuarioDTO usuarioDTO, @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7)); // Quitar "Bearer "
        userService.completarDetalleUsuario(username, usuarioDTO);
        return ResponseEntity.ok("Detalle de usuario actualizado");
    }


    @PostMapping("/detalle/vendedor")
    public ResponseEntity<String> completarDetalleVendedor(@RequestBody VendedorDTO vendedorDTO, @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        userService.completarDetalleVendedor(username, vendedorDTO);
        return ResponseEntity.ok("Detalle de vendedor actualizado");
    }


    @GetMapping("/usuarios")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}

