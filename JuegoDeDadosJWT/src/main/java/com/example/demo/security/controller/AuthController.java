package com.example.demo.security.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.demo.security.dto.JwtDto;
import com.example.demo.security.dto.LoginUser;
import com.example.demo.security.dto.NewUser;
import com.example.demo.security.entity.Rol;
import com.example.demo.security.entity.Usuario;
import com.example.demo.security.enums.RolName;
import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.security.service.RolService;
import com.example.demo.security.service.UserService;
import com.example.demo.util.Message;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	
 @Autowired
PasswordEncoder passwordEncoder;

@Autowired
AuthenticationManager authenticationManager;

@Autowired
UserService userService;

@Autowired
RolService rolService;

@Autowired
JwtProvider jwtProvider;

@PostMapping("/new")
public ResponseEntity<?> nuevo(@Valid @RequestBody NewUser newUser, BindingResult bindingResult){
    if(bindingResult.hasErrors())
        return new ResponseEntity(new Message("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
    if(userService.existsByUsername(newUser.getUsername()))
        return new ResponseEntity(new Message("ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
    if(userService.existsByEmail(newUser.getEmail()))
        return new ResponseEntity(new Message("ese email ya existe"), HttpStatus.BAD_REQUEST); 
    Usuario user = new Usuario(newUser.getName(), newUser.getUsername(), newUser.getEmail(),
                         passwordEncoder.encode(newUser.getPassword()));
    Set<Rol> rols = new HashSet<>();
    //me lanza error aquí.
    rols.add(rolService.getByRolName(RolName.ROLE_USER).get()); 
    if(newUser.getRoles().contains("admin"))
        rols.add(rolService.getByRolName(RolName.ROLE_ADMIN).get());
    user.setRoles(rols);
    userService.save(user);
    return new ResponseEntity(new Message("usuario guardado"), HttpStatus.CREATED);
}

@PostMapping("/login")
public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
    if(bindingResult.hasErrors())
        return new ResponseEntity(new Message("campos mal puestos"), HttpStatus.BAD_REQUEST);
    Authentication authentication =
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtProvider.generateToken(authentication);
    UserDetails userDetails = (UserDetails)authentication.getPrincipal();
    JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
    return new ResponseEntity(jwtDto, HttpStatus.OK);
}

}
