package com.co.tiendas.indy.ccolombia.persisten;

import org.springframework.data.jpa.repository.JpaRepository;

import com.co.tiendas.indy.ccolombia.model.DetalleVendedor;

public interface DetalleVendedorRepository extends JpaRepository<DetalleVendedor, Long> { 
	
}
