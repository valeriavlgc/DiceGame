package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import com.example.demo.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface DatabaseUsers extends MongoRepository<User, Integer> {

}
