package com.example.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable {

@Id
@Column(name="id")
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int id;
@Column
private String name;
//cambiar import a sql?
@Column
private Date regisDate;
@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Roll> tiradas;

public User() {}

public User(String name){
	
	if(name == null) {
		name = "Anonymous";
	}

this.name = name;
this.regisDate = new Date();
	
}

//Borrar algunos?
public int getId() {
	return id;
}

public String getName() {
	return name;
}

public Date getRegisDate() {
	return regisDate;
}

public String getTiradas() {
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

public int calculateSuccess() {
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
	
}

@Override
public String toString() {
	return "User [id=" + id + ", name=" + name + ", regisDate=" + regisDate + ", tiradas=" + tiradas + "]";
}


}
