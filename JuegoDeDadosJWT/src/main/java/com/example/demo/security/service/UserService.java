package com.example.demo.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.security.entity.Usuario;
import com.example.demo.security.repository.UsuarioRepository;

@Service
@Transactional
public class UserService {
	
@Autowired
UsuarioRepository userRepository;

public Optional<Usuario> getByUsername (String username) { 
	return userRepository.findByUsername(username);
}

public boolean existsByUsername(String username) {
	return userRepository.existsByUsername(username);
}

public boolean existsByEmail(String email) {
	return userRepository.existsByEmail(email);
}

public void save(Usuario user) {
userRepository.save(user);
}

}
