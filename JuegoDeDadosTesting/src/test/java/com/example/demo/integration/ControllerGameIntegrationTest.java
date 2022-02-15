package com.example.demo.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.assertj.core.api.StringAssert;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.demo.entity.Roll;
import com.example.demo.entity.User;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class ControllerGameIntegrationTest {

	@Test
	@Order(8)
	public void integrationTest_newUser() throws JSONException {


		User user = new User("Julia");
//poner la fecha correcta del día en que ejecuto.
		String expectedResult = "{\r\n" + "    \"id\": 5,\r\n" + "    \"name\": \"Julia\",\r\n"
				+ "    \"regisDate\": \"2022-02-15\",\r\n" + "    \"tiradas\": \"[]\"\r\n" + "}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> request = new HttpEntity<User>(user, headers);

		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/dice/user/Julia", request,
				String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		JSONAssert.assertEquals(expectedResult, response.getBody(), false);

	}

//put
	@Test
	@Order(7)
	public void integrationTest_modifyName() throws JSONException {

		User user = new User(4, "Pedro");

		String expectedResult = "{\r\n" + "    \"id\": 4,\r\n" + "    \"name\": \"Pedro\",\r\n"
				+ "    \"regisDate\": \"2018-07-04\",\r\n"
				+ "    \"tiradas\": \"IdRoll= 10[Dice1: 3Dice2: 4]\\nIdRoll= 11[Dice1: 3Dice2: 2]\\nIdRoll= 12[Dice1: 1Dice2: 1]\\n\"\r\n"
				+ "}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> request = new HttpEntity<User>(user, headers);

		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/dice/user/4/Pedro",
				HttpMethod.PUT, request, String.class);

		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		assertEquals(HttpStatus.FOUND, response.getStatusCode());
		JSONAssert.assertEquals(expectedResult, response.getBody(), false);

	}

//PostMapping Testeo más bien si ha añadido la tirada.
	@Test
	@Order(6)
	public void integrationTest_rollDice() throws JSONException {
		User user = new User(1, "Juan");
		Roll roll = new Roll(5, 2, 5, user);
//String expectedResult = "Roll de jugador Juan(id=1): [RollId=5, dice1=2, dice2=5] -> ***7**** ÉXITO!";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(roll.toString(), headers);

		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/dice/game/1", request,
				String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

//assertEquals(expectedResult, response.getBody());
		assertEquals(HttpStatus.FOUND, response.getStatusCode());

	}

//DeleteMapping
	@Test
	@Order(9)
	public void integrationTest_deleteRolls() {

		User user = new User(5, "Julia");

		String expectedResult = "Tiradas de Julia borradas con éxito";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(user.getName(), headers);

		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/dice/game/5", HttpMethod.DELETE,
				request, String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		assertEquals(expectedResult, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	@Order(1)
	public void integrationTest_showRolls() {

		String expectedResult = "IdRoll= 1[Dice1: 3Dice2: 3]\n" + "IdRoll= 2[Dice1: 3Dice2: 4]\n"
				+ "IdRoll= 3[Dice1: 2Dice2: 5]\n" + "IdRoll= 4[Dice1: 2Dice2: 1]\n" + "";

		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/dice/rolls/1", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

//JSONAssert.assertEquals(expectedResult, response.getBody(), false);
		assertEquals(expectedResult, response.getBody());

	}

	@Test
	@Order(2)
	public void integrationTest_percentage() {
//Funciona quitando las barras \r.
		String expectedResult = "Juan: 50%\n" + "Ana: 0%\n" + "Lucia: 100%\n" + "Milu: 33%";

		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/dice/percentage",
				String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		assertEquals(expectedResult, response.getBody());

	}

	@Test
	@Order(3)
	public void integrationTest_totalPercentage() {

		String expectedResult = "El porcentaje medio de éxito es: 45%";

		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/dice/totalPercentage",
				String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		assertEquals(expectedResult, response.getBody());

	}

	@Test
	@Order(4)
	public void IntegrationTest_winner() throws JSONException {

		String expectedResult = "{\r\n" + "    \"id\": 3,\r\n" + "    \"name\": \"Lucia\",\r\n"
				+ "    \"regisDate\": \"2018-07-04\",\r\n"
				+ "    \"tiradas\": \"IdRoll= 7[Dice1: 3Dice2: 4]\\nIdRoll= 8[Dice1: 6Dice2: 1]\\nIdRoll= 9[Dice1: 5Dice2: 2]\\n\"\r\n"
				+ "}";

		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/dice/winner", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		JSONAssert.assertEquals(expectedResult, response.getBody(), false);

	}

	@Test
	@Order(5)
	public void IntegrationTest_loser() throws JSONException {

		String expectedResult = "{\r\n" + "    \"id\": 2,\r\n" + "    \"name\": \"Ana\",\r\n"
				+ "    \"regisDate\": \"2018-07-04\",\r\n"
				+ "    \"tiradas\": \"IdRoll= 5[Dice1: 1Dice2: 5]\\nIdRoll= 6[Dice1: 4Dice2: 2]\\n\"\r\n" + "}";

		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/dice/loser", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		JSONAssert.assertEquals(expectedResult, response.getBody(), false);

	}

}
