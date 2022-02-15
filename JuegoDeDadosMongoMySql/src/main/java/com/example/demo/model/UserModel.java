package com.example.demo.model;

import java.util.Date;
import java.util.List;

import com.example.demo.document.Roll;

public class UserModel {

private int id;
private String name;
private Date regisDate;
private List<RollModel> tiradas;

public UserModel() {}

public UserModel (String name) {
	
	if(name == null || name.equals("")) {
		name = "Anonymous";
	}

this.name = name;
this.regisDate = new Date();
	
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Date getRegisDate() {
	return regisDate;
}
public void setRegisDate(Date regisDate) {
	this.regisDate = regisDate;
}

public List<RollModel> getRolls() {
	return tiradas;
}

public String getTiradas() {
String tiradasUser = "";
int contador = 0;
//contador para decir el número de tirada. 

	if(tiradas == null) {
		tiradasUser = "[]";
	} else {
		
		for (RollModel r : tiradas) {
			contador += 1;
			tiradasUser += "IdRoll= " + contador + "[Dice1: " + r.getDice1() + "Dice2: " + r.getDice2() +"]\n";
		}
	 }

	return tiradasUser;
}

public void setTiradas(List<RollModel> tiradas) {
	this.tiradas = tiradas;
}

public int calculateSuccess() {
int success = 0;
int percentage;
String percentage1;
int size = tiradas.size();
	
	for (RollModel r : tiradas) {
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

@Override
public String toString() {
	return "UserModel [name=" + name + ", regisDate=" + regisDate + "]";
}


	
}
