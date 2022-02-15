package com.example.demo.service;

import com.example.demo.document.Roll;
import com.example.demo.document.User;

public interface IDatabaseGame {

public void save(User user);
public void saveRoll(Roll roll);
public User findById(int id);
public String showPercentage();
public String showTotalPercentage();
public User showLoser();
public User showWinner();
public void save(Roll roll);		
void deleteByUser(int user_id);

}
