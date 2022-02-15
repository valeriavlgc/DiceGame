package com.example.demo.security.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.security.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Integer> {

	Optional<Usuario> findByUsername (String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
