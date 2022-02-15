package com.example.demo.entity;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import com.example.demo.document.Roll;

@ComponentScan(basePackages = "com.example.demo")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {UserTest.class})
public class UserTest {

///MAL!
List<Roll> tiradas;

	@Test
	public int calculateSuccess() {
		int success = 0;
		int percentage;
		String percentage1;
			
			for (Roll r : tiradas) {
				
				if (r.getDice1() + r.getDice2() == 7) {
					success += 1;
				}
			}
		    
			if (success == 0) {
			   percentage = 0;
			} else {
			   percentage = (int)(success / tiradas.size()) *100;
			}
			


		   return percentage;
			
		}
	


}
