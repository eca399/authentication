package com.coremaker.authentication;

import com.coremaker.authentication.entity.User;
import com.coremaker.authentication.model.SignUpModel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthenticationApplicationTests {

	@Test
	public void sampleLogin() {
		final SignUpModel signUpModel = new SignUpModel();
		signUpModel.setPassword("abc");
		signUpModel.setEmail("gigi@gmail.com");
		signUpModel.setName("Gigi");

		given().contentType("application/json")
				.body(signUpModel)
				.when().post("http://localhost:8080/auth/sign-up")
				.then().statusCode(200);


		final User user = new User(signUpModel);
		String responseBody = given().contentType("application/json")
				.body(user)
				.when().post("http://localhost:8080/auth/login")
				.then().statusCode(200).log().all().extract().response().getBody().asString();

		final String token = responseBody.substring(user.getEmail().length() + 1);
		given().contentType("application/json")
				.header("Authorization", "Bearer " + token)
				.when().get("http://localhost:8080/auth/user-details/Gigi")
				.then().statusCode(200);
	}
}