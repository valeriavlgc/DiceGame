package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.demo.security.entity.Rol;
import com.example.demo.security.enums.RolName;
import com.example.demo.security.service.RolService;

@Component
public class CreateRoles implements CommandLineRunner {

@Autowired
RolService rolService;
	
@Override
public void run(String... args) throws Exception {

Rol rolAdmin = new Rol(RolName.ROLE_ADMIN);
Rol rolUser = new Rol(RolName.ROLE_USER);

rolService.save(rolAdmin);
rolService.save(rolUser);
	
}

}
