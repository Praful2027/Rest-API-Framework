package com.demo.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class POSTRequest {
	
	@Test
	public void PostTest() {
		
		//Base URI
				RestAssured.baseURI = "https://maps.googleapis.com";
				
				//3 types of Parameters can be passed : Path, Query & Header
				//In POST requests, Path parameters cannot be passed. For Query parameter, key can be passed as part of the end point url
				//All other parameters should be passed as part of the body only

				//Provide all resources and parameters that are given
				given()
						.queryParam("key", "AIzaSyBZgzBrQy3YECiDziQco1RJNSRii8HguHg")
						.body(Payload.getPostData()).
				when()
						.post("/maps/api/place/nearbysearch/json").
				then()
						.assertThat()
						.statusCode(200)
						.and()
						.contentType(ContentType.JSON)
						.and()
						.body("status", equalTo("OK"));

	}

}
