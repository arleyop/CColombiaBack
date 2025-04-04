package com.co.tiendas.indy.ccolombia.persisten;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.co.tiendas.indy.ccolombia.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
