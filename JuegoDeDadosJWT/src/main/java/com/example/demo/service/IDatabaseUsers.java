package com.example.demo.service;

import com.example.demo.entity.User;

public interface IDatabaseUsers {

public void save(User user);	
public User findById(int id);
public String showPercentage();
public String showTotalPercentage();
public User showLoser();
public User showWinner();
}
