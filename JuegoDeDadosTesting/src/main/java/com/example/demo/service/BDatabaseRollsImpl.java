package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Roll;
import com.example.demo.repository.DatabaseRolls;

@Service
@Component
public class BDatabaseRollsImpl implements IDatabaseRolls {

@Autowired
DatabaseRolls db;

@Override
public void deleteByUser(int user_id) {
List<Roll> rolls = db.findAll();
int roll_id;
	
for (Roll r : rolls) {
   if (r.getUser().getId() == user_id) {
	   roll_id = r.getId();
	   db.deleteById(roll_id);
   }
}
	
}

@Override
public Roll save(Roll roll) {
db.save(roll);

   return roll;
}
}
