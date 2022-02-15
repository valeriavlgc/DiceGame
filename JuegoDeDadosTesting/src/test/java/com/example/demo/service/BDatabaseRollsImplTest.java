package com.example.demo.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.entity.Roll;
import com.example.demo.entity.User;
import com.example.demo.repository.DatabaseRolls;

@SpringBootTest(classes = {BDatabaseRollsImplTest.class})
public class BDatabaseRollsImplTest {
	
@Mock
DatabaseRolls rollsRepository;

@InjectMocks
BDatabaseRollsImpl rollService;

List<Roll> rolls;

@Test
public void test_deleteByUser() {
int user_id = 1;
rolls = new ArrayList<Roll>();
User user = new User(1, "Valeria");

Roll roll1 = new Roll(1, 3, 5, user);
Roll roll2 = new Roll(2, 6, 1, user);
rolls.add(roll1); rolls.add(roll2);

	when(rollsRepository.findAll()).thenReturn(rolls);
	rollService.deleteByUser(user_id);
	verify(rollsRepository, times(1)).deleteById(1);
	
}
	
@Test
public void test_save() {
User user = new User(1, "Valeria");
Roll roll1 = new Roll(1, 3, 5, user);

	when(rollsRepository.save(roll1)).thenReturn(roll1);
	assertEquals(roll1, rollService.save(roll1));	
}

}
