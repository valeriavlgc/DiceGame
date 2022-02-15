package com.example.demo.document;


import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "rolls")
public class Roll {
@Id
private int id;	
@Transient
public static final String SEQUENCE_NAME = "roll_seq";
@Field("dice1")
private int dice1;
@Field("dice2")
private int dice2;
@ManyToOne
@JoinColumn(name="userId")
@JsonIgnore
private int userId;

public Roll() {}

//Hace falta poner el constructor?
public Roll(int userId) {

this.userId  = userId;
this.dice1 = (int)(Math.random() * 6) + 1;
this.dice2 = (int)(Math.random() * 6) + 1;

}

public int getuserId() {
	return userId;
}

public void setIdUser(int userId1) {
	this.userId = userId1;
}


public int getId() {
	return id;
}

public void setId(int id) {
this.id = id;
}

public int getDice1() {
	return dice1;
}

public int getDice2() {
	return dice2;
}

public String result() {
String result;	
	if(dice1 + dice2 == 7) {
		result = "-> ***7**** ÉXITO!";
	} else {
		result = "-> ***" + (dice1 + dice2) + "*** FRACASO :(" ;
	}
	
	return result;	
}

@Override
public String toString() {
	return "Roll de jugador " + "(id=" + userId + "): " + "[RollId=" + id + ", dice1=" + dice1 + ", dice2=" + dice2 + "] " + result();
	//return "Roll de jugador " + user.getName() + "(id=" + user.getId() + "): " + "[RollId=" + id + ", dice1=" + dice1 + ", dice2=" + dice2 + "] " + result();
}

}
