package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.example.demo.entity.Roll;
import com.example.demo.entity.User;
import com.example.demo.service.BDatabaseRollsImpl;
import com.example.demo.service.BDatabaseUsersImpl;

@ComponentScan(basePackages = "com.example.demo")
@AutoConfigureMockMvc
@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ControllerGameTestMvc.class})
public class ControllerGameTestMvc {
	
@Autowired
MockMvc mockMvc;
	
@Mock
BDatabaseUsersImpl db1;

@Mock
BDatabaseRollsImpl db2;

@InjectMocks
ControllerGame controllerGame;

@BeforeEach
void setUp() {
	 mockMvc = MockMvcBuilders.standaloneSetup(controllerGame).build();
}

@Test
public void test_newUser() throws Exception {
String name = "Valeria";
User user = new User(name);
  
  when(db1.save(user)).thenReturn(user);
  this.mockMvc.perform(post("/dice/user/{name}", name))
			  .andExpect(status().isCreated())
			  .andExpect(MockMvcResultMatchers.jsonPath(".name").value("Valeria"))
			  .andDo(print());
}


@Test
public void test_modifyName() throws Exception {
int id = 1;
String name = "Valeria";
User user = new User(id, name);

	when(db1.findById(id)).thenReturn(user);
    this.mockMvc.perform(put("/dice/user/{user_id}/{name}", id, name))
			    .andExpect(status().isFound())
			    .andExpect(MockMvcResultMatchers.jsonPath(".name").value("Valeria"))
			    .andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
			    .andDo(print());
				
	
}


//No funciona siempre se ejecuta la tirada random.
@Test
public void test_rollDice () throws Exception {
int id = 1; int roll_id = 1;
String name = "Valeria";
User user = new User(id,name);
int dice1 = 5, dice2 = 2;
Roll roll = new Roll(roll_id, dice1, dice2, user);

    when(db1.findById(id)).thenReturn(user);
   	when(db2.save(roll)).thenReturn(roll);
	this.mockMvc.perform(post("/dice/game/{user_id}", id))
			    .andExpect(status().isFound())
			    .andExpect(MockMvcResultMatchers.content().string(containsString("5")))
			    .andExpect(MockMvcResultMatchers.content().string(containsString("2")))
			    .andDo(print());

}

@Test 
public void test_deleteRolls() throws Exception {
User user = new User(1, "Valeria");
int id = 1;

    when(db1.findById(id)).thenReturn(user);
	this.mockMvc.perform(delete("/dice/game/{user_id}", id))
				.andExpect(status().isOk())
				.andDo(print());
}

@Test
public void test_percentage() throws Exception {
String percentage = "El porcentaje medio de éxito es: 50 %";
	
    when(db1.showTotalPercentage()).thenReturn(percentage);
	this.mockMvc.perform(get("/dice/percentage"))
				.andExpect(status().isOk())
				.andDo(print());
}

@Test
public void test_winner() throws Exception {
//User winner = new User(1, "Valeria");
	
    when(db1.showWinner()).thenReturn(new User(1,"Valeria"));
	this.mockMvc.perform(get("/dice/winner"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath(".name").value("Valeria"))
				.andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
				.andDo(print());
}

@Test
public void test_loser() throws Exception {
	
	when(db1.showLoser()).thenReturn(new User(1,"Valeria"));
	this.mockMvc.perform(get("/dice/loser"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath(".name").value("Valeria"))
				.andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
				.andDo(print());
}


}
