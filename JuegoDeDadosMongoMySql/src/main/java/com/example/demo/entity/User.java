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

import com.example.demo.document.Roll;

@Entity
@Table(name = "user")
public class User implements Serializable {

@Id
@Column(name="id")
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int id;
@Column
private String name;
@Column
private Date regisDate;


public User() {}

public User(String name){
	
	if(name == null) {
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

@Override
public String toString() {
	return "User [id=" + id + ", name=" + name + ", regisDate=" + regisDate + "]";
}


}
