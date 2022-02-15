package com.example.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.demo.document.Roll;
import com.example.demo.entity.User;
import com.example.demo.model.RollModel;
import com.example.demo.model.UserModel;
import com.example.demo.repository.RollsRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.GameService;


@Controller
@RequestMapping("/dice")
public class ControllerGame {

@Autowired
GameService gameService;


//Crear usuario
@PostMapping("/user/{name}")
public  ResponseEntity<User> newUser(@PathVariable(name = "name") String name) {

try {
	UserModel userMod = new UserModel(name);	
    User user = gameService.saveUser(userMod);
	return new ResponseEntity<User>(user, HttpStatus.CREATED); 
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
@PutMapping("/user/{user_id}/{name}")
public  ResponseEntity<User> modifyName(@PathVariable(name = "user_id") int id, @PathVariable(name = "name") String name) {

try {
	User user = gameService.findUserById(id);
	user.setName(name);
	return new ResponseEntity<User>(user, HttpStatus.FOUND);
} catch (NoSuchElementException e) {
	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}

//AÑADIR que ponga éxito o fracaso. 
//Tirar dados
@PostMapping("/game/{user_id}") 
public ResponseEntity<String> rollDice(@PathVariable(name = "user_id") int user_id) {
   
try {
	RollModel roll = new RollModel(user_id);
	gameService.saveRollMod(roll);
	return new ResponseEntity<String>(roll.toString(), HttpStatus.FOUND);
} catch (NoSuchElementException e) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}

//Elimina tiradas de un jugador
@DeleteMapping("/game/{user_id}")
public ResponseEntity<String> deleteRolls(@PathVariable(name = "user_id") int id) {
gameService.deleteRollByUser(id);

	return ResponseEntity.ok("Tiradas de " + gameService.findUserById(id).getName() + " borradas con éxito");	
}

//Muestra solo el nombre de los jugadores y el porcentaje de éxito.
@GetMapping("/percentage")
public ResponseEntity<String> showPercentage() {

	return ResponseEntity.ok(gameService.showPercentage());
}

//GET: Devuelve el listado de tiradas de un jugador 
@GetMapping("/rolls/{user_id}")
public ResponseEntity<String> showRolls(@PathVariable(name = "user_id") int id) {
      
	return ResponseEntity.ok(gameService.findRollsByUserId(id));	
}

//GET /players/ranking: retorna el ranking  mig de tots els   jugadors del sistema .  És a dir, el  percentatge mig  d’èxits. 
@GetMapping("/totalPercentage")
public ResponseEntity<String> showTotalPercentage() {
	
	return ResponseEntity.ok(gameService.showTotalPercentage());
}

//GET /players/ranking/loser: retorna el jugador  amb pitjor percentatge d’èxit 
@GetMapping("/loser")
public ResponseEntity<UserModel> showLoser() {
	
	return ResponseEntity.ok(gameService.showLoser());
}

//GET /players/ranking/winner: retorna el  jugador amb  mejor percentatge   d’èxit 
@GetMapping("/winner")
public ResponseEntity<UserModel> showWinner() {
	
	return ResponseEntity.ok(gameService.showWinner());
}
}
