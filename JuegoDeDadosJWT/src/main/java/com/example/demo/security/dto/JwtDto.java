package com.example.demo.security.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

//una vez hacemos un logic nos devuelve un jwtdto.

public class JwtDto {
	
private String token;
private String bearer = "Bearer";
private String username;
private Collection<? extends GrantedAuthority> authorities;

public JwtDto(String token, String username, Collection<? extends GrantedAuthority> authorities) {
    this.token = token;
    this.username = username;
    this.authorities = authorities;
}

public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

public String getBearer() {
    return bearer;
}

public void setBearer(String bearer) {
    this.bearer = bearer;
}

public String getUsername() {
    return username;
}

public void setNombreUsuario(String nombreUsuario) {
    this.username = username;
}

public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
}

public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    this.authorities = authorities;
}
}
