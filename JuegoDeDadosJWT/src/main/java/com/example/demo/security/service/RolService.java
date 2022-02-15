package com.example.demo.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.security.entity.Rol;
import com.example.demo.security.enums.RolName;
import com.example.demo.security.repository.RolRepository;

@Service
@Transactional
public class RolService {

@Autowired
RolRepository rolRepository;	

public Optional<Rol> getByRolName (RolName rolName) {
	return rolRepository.findByRolName(rolName);
} 

public void save(Rol rol) {
	rolRepository.save(rol);
}



}
