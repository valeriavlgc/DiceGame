package com.example.demo.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.security.entity.MainUser;
import com.example.demo.security.entity.Usuario;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

@Autowired
UserService userService;
	
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
Usuario user = userService.getByUsername(username).get();
	
   return MainUser.build(user);
}
	
	
	

}
