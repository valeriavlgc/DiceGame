package com.example.demo.document;

import org.springframework.data.annotation.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


@Document(collection = "users")
public class User {

@Id
private int id;
@Transient
public static final String SEQUENCE_NAME = "user_seq";
private String name;
@DateTimeFormat(iso = ISO.DATE)
private Date regisDate;
//JSON ignore.
@DBRef(lazy = false)
private List<Roll> tiradas;

public User() {}

public User(String name){
	
	if(name == null) {
		name = "anonymous";
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

public Date getRegisDate() {
	return regisDate;
}

/*public String getTiradas() {
String tiradasUser = "";

	if(tiradas == null) {
		tiradasUser = "[]";
	} else {
		
		for (Roll r : tiradas) {
			//corregir /n en última linea con un if.  
			tiradasUser += "IdRoll= " + r.getId() + "[Dice1: " + r.getDice1() + "Dice2: " + r.getDice2() +"]\n";
		}
	 }

	return tiradasUser;
}*/

public void addRoll (Roll roll) {
	if (tiradas == null) {
		tiradas = new ArrayList<Roll>();
	}
	tiradas.add(roll);
	roll.setIdUser(this.id);
}

public void setName(String name) {
	this.name = name;
}

public void setRegisDate(Date regisDate) {
	this.regisDate = regisDate;
}

public void setTiradas(List<Roll> tiradas) {
	this.tiradas = tiradas;
}

/*public int calculateSuccess() {
int success = 0;
int percentage;
String percentage1;
int size = tiradas.size();
	
	for (Roll r : tiradas) {
		
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
	
}*/

@Override
public String toString() {
	return "User [id=" + id + ", name=" + name + ", regisDate=" + regisDate + ", tiradas=" + tiradas + "]";
}



}
