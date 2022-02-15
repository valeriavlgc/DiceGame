package com.example.demo.service;

import com.example.demo.entity.Roll;
import com.example.demo.entity.User;

public interface IDatabaseRolls {

public Roll save(Roll roll);		
void deleteByUser(int user_id);

}
