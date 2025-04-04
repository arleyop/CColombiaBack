package com.co.tiendas.indy.ccolombia.persisten;

import org.springframework.data.jpa.repository.JpaRepository;

import com.co.tiendas.indy.ccolombia.model.RolEntity;

import java.util.Optional;

public interface RolRepository extends JpaRepository<RolEntity, Long> {
    Optional<RolEntity> findByNombre(String nombre);
}
