package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.document.Roll;
import com.example.demo.document.User;
import com.example.demo.repository.DatabaseRolls;
import com.example.demo.repository.DatabaseUsers;
import com.example.demo.service.BDatabaseGameImpl;
import com.example.demo.service.IDatabaseGame;

@Controller
@RequestMapping("/dice")
public class ControllerGame {

@Autowired
BDatabaseGameImpl db1;

/*@Autowired
public ControllerGame(BDatabaseUsersImpl db1, BDatabaseRollsImpl db2) {
	this.db1 = db1;
	this.db2 = db2;
}*/


//Crear usuario
@PostMapping("/user")
public  ResponseEntity<User> newUser(@RequestBody User user) {
db1.save(user);	
	return ResponseEntity.ok(user);
}

//Modificar nombre usuario
@PutMapping("/modifyUser/{user_id}/{name}")
public  ResponseEntity<User> modifyName(@PathVariable(name = "user_id") int id, @PathVariable(name = "name") String name) {
User user = db1.findById(id);
user.setName(name);
//No hace falta volver a guardar en database?
	return ResponseEntity.ok(user);
}

//Tirar dados
//NO FUNCIONA!!!!!!!!!!!!!!!!!!!!!!!
@PostMapping("/game/{user_id}") 
public ResponseEntity<String> rollDice(@PathVariable(name = "user_id") int id) {
//User user = db1.findById(id);
Roll roll = new Roll(id);

db1.save(roll);
db1.saveRoll(roll);

   return ResponseEntity.ok(roll.toString());
}

//Elimina tiradas de un jugador
@DeleteMapping("/game/{user_id}")
public ResponseEntity<User> deleteRolls(@PathVariable(name = "user_id") int id) {
db1.deleteByUser(id);

	return ResponseEntity.ok(db1.findById(id));	
}

//Muestra solo el nombre de los jugadores y el porcentaje de éxito.
@GetMapping("/percentage")
public ResponseEntity<String> showPercentage() {

	return ResponseEntity.ok(db1.showPercentage());
}

//Devuelve el listado de tiradas de un jugador 
@GetMapping("/rolls/{user_id}")
public ResponseEntity<String> showRolls(@PathVariable(name = "user_id") int id) {
      
	return ResponseEntity.ok(db1.getTiradas(id));	
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
