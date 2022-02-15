package com.example.demo.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.entity.Roll;
import com.example.demo.entity.User;
import com.example.demo.repository.DatabaseUsers;

@SpringBootTest(classes = {BDatabaseUsersImplTest.class})
public class BDatabaseUsersImplTest {

@Mock 	
DatabaseUsers userRepository;	

@InjectMocks
BDatabaseUsersImpl userService;

List<User> users;
List<Roll> rolls;
List<Roll> rolls1;

@Test
public void test_save() {
User user = new User("Valeria");

	when(userRepository.save(user)).thenReturn(user);
	assertEquals(user, userService.save(user));
}

@Test
public void test_showPercentage() {
String percentage = "Valeria: 50%";
User user1 = new User("Valeria");

Roll rol1 = new Roll(1, 3, 4, user1), rol3 = new Roll(1, 2, 4, user1);

users = new ArrayList<User>();
rolls1 = new ArrayList<Roll>(); rolls1.add(rol1);  rolls1.add(rol3);
user1.setTiradas(rolls1);
users.add(user1); 
	
	when(userRepository.findAll()).thenReturn(users);
	assertEquals(percentage, userService.showPercentage());
}

@Test
public void test_showTotalPercentage() {
String percentage = "El porcentaje medio de éxito es: 50%";
User user1 = new User("Valeria");

Roll rol1 = new Roll(1, 3, 4, user1), rol3 = new Roll(1, 2, 4, user1);

users = new ArrayList<User>();
rolls1 = new ArrayList<Roll>(); rolls1.add(rol1);  rolls1.add(rol3);
user1.setTiradas(rolls1);
users.add(user1); 
	
	when(userRepository.findAll()).thenReturn(users);
	assertEquals(percentage, userService.showTotalPercentage());
}


@Test
public void test_showLoser() {
User user = new User("Valeria");
User user1 = new User("Juan");
Roll rol1 = new Roll(1, 3, 4, user), 
rol2 = new Roll(1, 3, 4, user1), rol3 = new Roll(1, 2, 4, user);


users = new ArrayList<User>();
rolls = new ArrayList<Roll>(); rolls.add(rol3); 
rolls1 = new ArrayList<Roll>(); rolls1.add(rol1); rolls.add(rol2);
user.setTiradas(rolls); user1.setTiradas(rolls1);
users.add(user1); users.add(user);

	when(userRepository.findAll()).thenReturn(users);
	assertEquals(user, userService.showLoser());
}

@Test
public void test_showWinner() {
User user = new User("Valeria");
User user1 = new User("Juan");
Roll rol1 = new Roll(1, 3, 4, user), 
rol2 = new Roll(1, 3, 4, user1), rol3 = new Roll(1, 2, 4, user);


users = new ArrayList<User>();
rolls = new ArrayList<Roll>(); rolls.add(rol3); 
rolls1 = new ArrayList<Roll>(); rolls1.add(rol1); rolls.add(rol2);
user.setTiradas(rolls); user1.setTiradas(rolls1);
users.add(user1); users.add(user);

	when(userRepository.findAll()).thenReturn(users);
	assertEquals(user1, userService.showWinner());
}

	
}
