package com.example.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Roll;
import com.example.demo.entity.User;
import com.example.demo.repository.DatabaseRolls;
import com.example.demo.repository.DatabaseUsers;
import com.example.demo.service.BDatabaseRollsImpl;
import com.example.demo.service.BDatabaseUsersImpl;
import com.example.demo.service.IDatabaseUsers;

@RestController
@RequestMapping("/dice")
//@CrossOrigin(origins = "*")
public class ControllerGame {

@Autowired
BDatabaseUsersImpl db1;

@Autowired
BDatabaseRollsImpl db2;

/*@Autowired
public ControllerGame(BDatabaseUsersImpl db1, BDatabaseRollsImpl db2) {
	this.db1 = db1;
	this.db2 = db2;
}*/

//Crear usuario
@PreAuthorize("hasRole('ADMIN')")
@PostMapping("/user/{name}")
public  ResponseEntity<User> newUser(@PathVariable(name = "name") String name) {

try {
	User user = new User(name);	
    db1.save(user);
	return new ResponseEntity<User>(user,HttpStatus.CREATED); 
} catch (NoSuchElementException e) {
	return new ResponseEntity<>(HttpStatus.CONFLICT); 
  }

}

//@PostMapping("/user" "/user/") -> no puedo dos rutas. 
@RequestMapping(value = {"/user", "/user/"}, method = RequestMethod.POST)
public ResponseEntity<User> namelessNewUser() {
String name = null;
ResponseEntity<User> response = newUser(name);
   return response;
}

//Modificar nombre usuario
@PreAuthorize("hasRole('ADMIN')")
@PutMapping("/user/{user_id}/{name}")
public  ResponseEntity<User> modifyName(@PathVariable(name = "user_id") int id, @PathVariable(name = "name") String name) {

try {
	User user = db1.findById(id);
	user.setName(name);
	return new ResponseEntity<User>(user, HttpStatus.FOUND);
} catch (NoSuchElementException e) {
	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}

//AÑADIR que ponga éxito o fracaso. 
//Tirar dados
@PostMapping("/game/{user_id}") 
public ResponseEntity<String> rollDice(@PathVariable(name = "user_id") int id) {
   
try {
	User user = db1.findById(id);
	Roll roll = new Roll(user);
	db2.save(roll);
	return new ResponseEntity<String>(roll.toString(), HttpStatus.FOUND);
} catch (NoSuchElementException e) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}

//Elimina tiradas de un jugador
@DeleteMapping("/game/{user_id}")
public ResponseEntity<String> deleteRolls(@PathVariable(name = "user_id") int id) {
db2.deleteByUser(id);

	return ResponseEntity.ok("Tiradas de " + db1.findById(id).getName() + " borradas con éxito");	
}

//Muestra solo el nombre de los jugadores y el porcentaje de éxito.
@GetMapping("/percentage")
public ResponseEntity<String> showPercentage() {

	return ResponseEntity.ok(db1.showPercentage());
}

//Devuelve el listado de tiradas de un jugador 
@GetMapping("/rolls/{user_id}")
public ResponseEntity<String> showRolls(@PathVariable(name = "user_id") int id) {
      
	return ResponseEntity.ok(db1.findById(id).getTiradas());	
}

//GET /players/ranking: retorna el ranking  mig de tots els   jugadors del sistema .  És a dir, el  percentatge mig  d’èxits. 
@GetMapping("/totalPercentage")
public ResponseEntity<String> showTotalPercentage() {
	
	return ResponseEntity.ok(db1.showTotalPercentage());
}

//GET /players/ranking/loser: retorna el jugador  amb pitjor percentatge d’èxit 
@GetMapping("/loser")
public ResponseEntity<User> showLoser() {
	
	return ResponseEntity.ok(db1.showLoser());
}

//GET /players/ranking/winner: retorna el  jugador amb  mejor percentatge   d’èxit 
@GetMapping("/winner")
public ResponseEntity<User> showWinner() {
	
	return ResponseEntity.ok(db1.showWinner());
}

}
