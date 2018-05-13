package com.demo.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import files.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddDeletePlace {
	
	Properties p = new Properties();
	
	@BeforeTest
	public void getData() throws IOException {
		
		FileInputStream fis = new FileInputStream("P:\\Eclipse Workspace\\RestAssured\\src\\test\\java\\files\\env.properties");
		p.load(fis);
		//p.get("HOST");
	}

	@Test
	public void AddAndDeletePlace() {

		//Base URI
		RestAssured.baseURI = p.getProperty("HOST");

		Response res = given()
				.queryParam("key", p.getProperty("KEY"))
				.body(Payload.getPostData()).
		when()
				.post(Resources.placePostData()).
		then()
				.assertThat()
				.statusCode(200)
				.and()
				.contentType(ContentType.JSON)
				.and()
				.body("status", equalTo("OK")).
		extract()
				.response();
		
		//Convert response into string
		String resStr = res.asString();
		System.out.println(resStr);
		
		//Pick the place_id from the response body
		JsonPath js = new JsonPath(resStr);
		String placeid = js.get("place_id");
		
		String placeBody = "{"+
								"\"place_id\": \""+placeid+"\""+
							"}";
		//Delete the place
		given()
				.queryParam("key", "AIzaSyBZgzBrQy3YECiDziQco1RJNSRii8HguHg")
				.body(placeBody).
		when()
				.post("/maps/api/place/delete/json").
		then()
				.assertThat().statusCode(200);

	}

}
