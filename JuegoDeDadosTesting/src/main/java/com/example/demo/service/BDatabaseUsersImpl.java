package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.DatabaseUsers;

@Service
@Component
public class BDatabaseUsersImpl implements IDatabaseUsers {

@Autowired
DatabaseUsers db;

@Override
public User save(User user) {
db.save(user);	
    return user;
}

@Override
public User findById(int id) {
//manejar optional exception
return db.findById(id).get();
}

@Override
public String showPercentage() {
List<User> users = db.findAll();

String percentageUsers = users.stream()
                              .map(u -> u.getName() + ": " + u.calculateSuccess() + "%")
                              .collect(Collectors.joining("\n"));


	return percentageUsers;
}

@Override
public String showTotalPercentage() {
List<User> users = db.findAll();

Double percentage = users.stream()
                           .map(u -> u.calculateSuccess())
                           .collect(Collectors.averagingInt(u -> u));

int totalPercentage = percentage.intValue();

return "El porcentaje medio de éxito es: " + totalPercentage + "%";
}

@Override
public User showLoser() {
List<User> users = db.findAll();	
int worstPercentage = 100;
User userLoser = null;

for (User u : users) {
	if (u.calculateSuccess() < worstPercentage) {
		worstPercentage = u.calculateSuccess();
		userLoser = u;
	}
}

	return userLoser;
}

@Override
public User showWinner() {
List<User> users = db.findAll();	
int bestPercentage = 0;
User userWinner = null;

for (User u : users) {
	if (u.calculateSuccess() > bestPercentage) {
		bestPercentage = u.calculateSuccess();
		userWinner = u;
	}
}

	return userWinner;
}

}
