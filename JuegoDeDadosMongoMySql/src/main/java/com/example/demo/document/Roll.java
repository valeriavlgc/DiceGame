package com.example.demo.document;

import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.mongodb.lang.NonNull;

@Document(collection = "rolls")
public class Roll {

@Id
@NonNull
private String id;	

@Field("dice1")
private int dice1;

@Field("dice2")
private int dice2;

@Field("userId")
private int userId;

public Roll() {}

public Roll(int userId, int dice1, int dice2) {
	this.dice1 = dice1; 
    this.dice2 = dice2;
	this.userId = userId;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public int getDice1() {
	return dice1;
}

public void setDice1(int dice1) {
	this.dice1 = dice1;
}

public int getDice2() {
	return dice2;
}

public void setDice2(int dice2) {
	this.dice2 = dice2;
}

public int getUserId() {
	return userId;
}

public void setUserId(int userId) {
	this.userId = userId;
}

@Override
public String toString() {
	return "Roll de jugador " + "(id=" + userId + "): " + "[RollId=" + id + ", dice1=" + dice1 + ", dice2=" + dice2 + "] ";
}

}
