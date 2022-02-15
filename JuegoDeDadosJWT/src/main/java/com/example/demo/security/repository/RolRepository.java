package com.example.demo.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.security.entity.Rol;
import com.example.demo.security.enums.RolName;

@Repository
public interface RolRepository extends JpaRepository <Rol, Integer>{

Optional<Rol> findByRolName(RolName rolName);

}
