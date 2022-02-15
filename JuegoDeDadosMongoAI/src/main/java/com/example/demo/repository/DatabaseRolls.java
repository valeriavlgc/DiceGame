package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.document.Roll;

@Repository
public interface DatabaseRolls extends MongoRepository<Roll, Integer> {
	
public List<Roll> findRollByUserId(int userId);

}
