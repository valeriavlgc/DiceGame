package com.example.demo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "sequences_db")
public class Sequence {
	
@Id 
private String id;
@Field("seq_number")
private int value;

public Sequence () {}

public String getId() {
	return id;
}

public int getValue() {
	return value;
}

public void setId(String id) {
	this.id = id;
}

public void setValue(int value) {
	this.value = value;
}



}
