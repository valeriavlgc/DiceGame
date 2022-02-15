package com.example.demo.model;

import javax.persistence.Id;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

import com.example.demo.entity.User;

public class RollModel {

private String id;
private int dice1;
private int dice2;
private int userId;

public RollModel() {}

public RollModel (int userId) {

this.userId  = userId;
this.dice1 = (int)(Math.random() * 6) + 1;
this.dice2 = (int)(Math.random() * 6) + 1;

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
public int getuserId() {
	return userId;
}
public void setuserId(int userId) {
	this.userId = userId;
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
	return "Roll de jugador " + "(id=" + userId + "): " + "[dice1=" + dice1 + ", dice2=" + dice2 + "] " + result();
}
	
	
	
}
