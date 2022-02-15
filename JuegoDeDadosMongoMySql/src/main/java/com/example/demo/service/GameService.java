package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.document.Roll;
import com.example.demo.entity.User;
import com.example.demo.model.RollModel;
import com.example.demo.model.UserModel;
import com.example.demo.repository.RollsRepository;
import com.example.demo.repository.UsersRepository;

@Service
public class GameService {
	
@Autowired
RollsRepository dbRolls;
@Autowired
UsersRepository dbUsers;

//Convierte lista de usuarios en lista de usuariosModelo con tiradas incluidas.
public List<UserModel> findAllUserMod() {
List<UserModel> userModList = new ArrayList<UserModel>();
List<User> userList;

try {
userList = dbUsers.findAll();
} catch (Exception e) {
	throw e;
}

if (userList.size() > 0) {
userList.stream()
		.forEach(u -> {
		UserModel userMod = new UserModel();
		userMod.setId(u.getId());
		userMod.setName(u.getName());
		userMod.setRegisDate(u.getRegisDate());

		List<RollModel> rollsMod = new ArrayList<RollModel>();
		List<Roll> rolls = dbRolls.findRollByUserId(userMod.getId());
		
		rolls.stream()
		     .forEach(r -> {
		     RollModel rolMod = new RollModel();
		     BeanUtils.copyProperties(r, rolMod);
		     rollsMod.add(rolMod);});
		
		userMod.setTiradas(rollsMod);
		userModList.add(userMod);
		
		});
}

	return userModList;
}

//Métodos propios de Roll

//Guardo tirada.
public void saveRollMod(RollModel rollMod) {
Roll roll = new Roll();
BeanUtils.copyProperties(rollMod, roll);
//ya lo hace arriba, quitar.
roll.setUserId(rollMod.getuserId());


	try {
	  dbRolls.save(roll);
	} catch (Exception e) {
		throw e;
	}

}

public String findRollsByUserId(int id) {
	List<UserModel> users = findAllUserMod();
	String tiradas = "";
	
	for (UserModel u : users) {
		if(u.getId() == id) {
			tiradas = u.getTiradas();	
		}
	}

	return tiradas;
}

//Borrar tiradas de un jugador
public void deleteRollByUser(int user_id) {
List<Roll> rolls = dbRolls.findAll();
String roll_id;
		
	for (Roll r : rolls) {
	   if (r.getUserId() == user_id) {
		   roll_id = r.getId();
		   dbRolls.deleteById(roll_id);
	   }
	}
		
}

//Métodos propios de clase User

//Crear jugador
public User saveUser(UserModel userMod) {

User user = new User();
BeanUtils.copyProperties(userMod, user);
try {
dbUsers.save(user);	

if (userMod.getRolls() != null) {
	userMod.getRolls()
	       .stream()
	       .forEach(r -> {
		    Roll roll = new Roll();
		    r.setuserId(userMod.getId());
		    BeanUtils.copyProperties(r, roll);
		    try {
		        dbRolls.save(roll);
		    } catch (Exception e) {
		 	   throw e;
		    }
		    });
}

} catch (Exception e) {
	throw e;
}
	
 return user;
}

public User findUserById(int id) {
	return dbUsers.findById(id).get();
}

public String showPercentage() {
List<UserModel> users = findAllUserMod();                                

String percentageUsers = users.stream()
                              .map(u -> u.getName() + ": " + u.calculateSuccess() + "%")
                              .collect(Collectors.joining("\n"));

	return percentageUsers;
}


public String showTotalPercentage() {
List<UserModel> users = findAllUserMod();

Double percentage = users.stream()
                         .map(u -> u.calculateSuccess())
                         .collect(Collectors.averagingInt(u -> u));

int totalPercentage = percentage.intValue();

return "El porcentaje medio de éxito es: " + totalPercentage + "%";

}

public UserModel showLoser() {
List<UserModel> users = findAllUserMod();	
int worstPercentage = 100;
UserModel modelUserLoser = null;

for (UserModel u : users) {
	if (u.calculateSuccess() < worstPercentage) {
		worstPercentage = u.calculateSuccess();
		modelUserLoser = u;
	}
}  

	return modelUserLoser;
}

public UserModel showWinner() {
List<UserModel> users = findAllUserMod();	
int bestPercentage = 0;
UserModel modelUserWinner = null;

for (UserModel u : users) {
	if (u.calculateSuccess() > bestPercentage) {
		bestPercentage = u.calculateSuccess();
		modelUserWinner = u;
	}
}

	return modelUserWinner;
}

}
