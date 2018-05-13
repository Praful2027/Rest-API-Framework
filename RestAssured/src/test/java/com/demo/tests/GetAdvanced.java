package com.demo.tests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import files.Reusables;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetAdvanced {
	
	@Test
	public void GetTest() {

		//Base URI
		RestAssured.baseURI = "https://maps.googleapis.com";

		//Provide all resources and parameters that are given
		Response res = given()
				.param("location", "17.3850,78.4867")
				.param("radius", "50")
				.param("key", "AIzaSyBZgzBrQy3YECiDziQco1RJNSRii8HguHg")
				.log().all().
		when()
				.get("/maps/api/place/nearbysearch/json").
		then()
				.assertThat()
				.statusCode(200)
				.and()
				.contentType(ContentType.JSON)
				.log()
				.all().
		extract()
				.response();
		
		JsonPath jp = Reusables.rawToJson(res);
		int count = jp.get("results.size()");
		System.out.println("Count: "+count);
		
		for(int i = 0; i<count; i++) {
			System.out.println(jp.get("results["+i+"].name"));
		}
		
	}

}
