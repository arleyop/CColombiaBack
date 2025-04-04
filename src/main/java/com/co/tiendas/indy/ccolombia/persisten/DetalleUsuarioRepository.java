package com.co.tiendas.indy.ccolombia.persisten;

import org.springframework.data.jpa.repository.JpaRepository;

import com.co.tiendas.indy.ccolombia.model.DetalleUsuario;

public interface DetalleUsuarioRepository extends JpaRepository<DetalleUsuario, Long> { 
	
}


