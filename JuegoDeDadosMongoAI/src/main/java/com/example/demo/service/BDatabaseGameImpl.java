package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.document.Roll;
import com.example.demo.document.User;
import com.example.demo.repository.DatabaseRolls;
import com.example.demo.repository.DatabaseUsers;

@Service
public class BDatabaseGameImpl implements IDatabaseGame {

@Autowired
DatabaseUsers db;

@Autowired
DatabaseRolls db1;

@Autowired
SequenceService sequenceService;

@Override
public void save(User user) {
user.setId(sequenceService.setNextSequence(user.SEQUENCE_NAME));
db.save(user);	
}

@Override
public User findById(int id) {
//manejar optional exception
return db.findById(id).get();
}

@Override
public void saveRoll(Roll roll) {
Optional<User> user = db.findById(roll.getuserId());
user.get().addRoll(roll);
}

@Override
public String showPercentage() {
List<User> users = db.findAll();
List<Roll> rolls;
int success;
String percentageUsers = "";

for (User u : users) {
	rolls = findUserRolls(u.getId());
	success = calculateSuccess(rolls);
	percentageUsers += u.getName() + ": " + success + "%\n";
}
	return percentageUsers;
}

@Override
public String showTotalPercentage() {
List<User> users = db.findAll();
List<Roll> rolls;
int success;
double percentage = 0;
int totalPercentage;

for (User u : users) {
	rolls = findUserRolls(u.getId());
	success = calculateSuccess(rolls);
	
	percentage += success; 
	
}

percentage = percentage / users.size();
totalPercentage = (int) percentage;

return "El porcentaje medio de éxito es: " + totalPercentage + "%";
}

@Override
public User showLoser() {
List<User> users = db.findAll();
List<Roll> rolls;
int success;
int worstPercentage = 100;
User userLoser = null;

for (User u : users) {
	rolls = findUserRolls(u.getId());
	success = calculateSuccess(rolls);
	if (success < worstPercentage) {
		worstPercentage = success;
		userLoser = u;
	}
}

	return userLoser;
}

@Override
public User showWinner() {
List<User> users = db.findAll();	
List<Roll> rolls;
int success;
int bestPercentage = 0;
User userWinner = null;

for (User u : users) {
	rolls = findUserRolls(u.getId());
	success = calculateSuccess(rolls);
	if (success > bestPercentage) {
		bestPercentage = success;
		userWinner = u;
	}
}

	return userWinner;
}

//Service rolls

@Override
public void deleteByUser(int user_id) {
List<Roll> rolls = db1.findAll();
int roll_id;
	
for (Roll r : rolls) {
   if (r.getuserId() == user_id) {
	   roll_id = r.getId();
	   db1.deleteById(roll_id);
   }
}
	
}

@Override
public void save(Roll roll) {
roll.setId(sequenceService.setNextSequence(roll.SEQUENCE_NAME));
db1.save(roll);
	
}


public List<Roll> findUserRolls(int userId) {
	return db1.findRollByUserId(userId);
}

public String getTiradas(int userId) {
List<Roll> rolls = findUserRolls(userId);

String tiradasUser = "";

if(rolls == null) {
	tiradasUser = "[]";
} else {
	
	for (Roll r : rolls) {
		//corregir /n en última linea con un if.  
		tiradasUser += "IdRoll= " + r.getId() + "[Dice1: " + r.getDice1() + "Dice2: " + r.getDice2() +"]\n";
	}
 }

return tiradasUser;
	
}

public int calculateSuccess (List<Roll> rolls) {
int success = 0;
int percentage;
String percentage1;
int size = rolls.size();
	
	for (Roll r : rolls) {
		
		if (r.getDice1() + r.getDice2() == 7) {
			success += 1;
		}
	}
    
	if (success == 0) {
	   percentage = 0;
	} else {
	   percentage = (int) (success*100)/size;
	}

   return percentage;
}

}
