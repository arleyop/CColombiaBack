package com.co.tiendas.indy.ccolombia.controller;

import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
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

import com.co.tiendas.indy.ccolombia.dto.AuthResponse;
import com.co.tiendas.indy.ccolombia.dto.UsuarioDTO;
import com.co.tiendas.indy.ccolombia.dto.VendedorDTO;
import com.co.tiendas.indy.ccolombia.model.UserEntity;
import com.co.tiendas.indy.ccolombia.security.JwtUtil;
import com.co.tiendas.indy.ccolombia.services.UserService;
import com.co.tiendas.indy.ccolombia.services.impl.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JwtUtil jwtUtil,UserService userService, CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService; 
        this.customUserDetailsService = customUserDetailsService;
        
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestParam String username, @RequestParam String password, @RequestParam String rol, HttpServletRequest request) {
        userDetailsService.registerUser(username, password, rol);
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtUtil.generateToken(username);
        
        AuthResponse response = new AuthResponse();
        response.setOk(true);
        response.setPath(request.getRequestURI());
        response.setMsg("Registro exitoso");
        response.setToken(token);
//        response.setId(user.getId().toString());
        response.setName(userDetails.getUsername());
//        response.setRole(userDetails.getRol().getNombre());
        return response;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            AuthResponse userDetails = customUserDetailsService.loadUserAuthResponse(username, request);
            return ResponseEntity.ok(userDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
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

