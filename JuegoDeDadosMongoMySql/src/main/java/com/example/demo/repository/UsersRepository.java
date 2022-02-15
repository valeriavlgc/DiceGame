package com.example.demo.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;
import com.example.demo.model.UserModel;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<User, Integer> {
	
//boolean existsByName(String nombre);




}
