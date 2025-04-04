package com.co.tiendas.indy.ccolombia.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;



@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private RolEntity rol;
    
    
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private DetalleUsuario detalleUsuario;

    @OneToOne(mappedBy = "vendedor", cascade = CascadeType.ALL)
    private DetalleVendedor detalleVendedor;
    
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RolEntity getRol() {
		return rol;
	}

	public void setRol(RolEntity rol) {
		this.rol = rol;
	}
    
    
}
