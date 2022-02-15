package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Roll implements Serializable {

@Id
@Column(name="id")
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int id;	
@Column(name="dice1")
private int dice1;
@Column(name="dice2")
private int dice2;
@ManyToOne()
@JoinColumn(name="user_id")
private User user;

public Roll() {}

//Hace falta poner el constructor?
public Roll(User user) {

this.user  = user;
this.dice1 = (int)(Math.random() * 6) + 1;
this.dice2 = (int)(Math.random() * 6) + 1;

}

public User getUser() {
	return user;
}


public int getId() {
	return id;
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
	return "Roll de jugador " + user.getName() + "(id=" + user.getId() + "): " + "[RollId=" + id + ", dice1=" + dice1 + ", dice2=" + dice2 + "] " + result();
}

}
