package com.demo.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class GETRequest {

	@Test
	public void GetTest() {

		//Base URI
		RestAssured.baseURI = "https://maps.googleapis.com";

		//Provide all resources and parameters that are given
		given()
				.param("location", "17.3850,78.4867")
				.param("radius", "50")
				.param("key", "AIzaSyBZgzBrQy3YECiDziQco1RJNSRii8HguHg").
		when()
				.get("/maps/api/place/nearbysearch/json").
		then()
				.assertThat()
				.statusCode(200)
				.and()
				.contentType(ContentType.JSON)
				.and()
				.body("results[0].name", equalTo("Hyderabad - Janagam Highway"))
				.and()
				.body("results[0].place_id", equalTo("ChIJ8f1Whs2ZyzsR1ZmoR3PVOGQ"))
				.and()
				.header("Server", "scaffolding on HTTPServer2");

	}

}
