package com.demo.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.Payload;
import files.Resources;
import files.Reusables;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class PostTestXML {

	Properties p = new Properties();

	@BeforeTest
	public void getData() throws IOException {

		FileInputStream fis = new FileInputStream("P:\\Eclipse Workspace\\RestAssured\\src\\test\\java\\files\\env.properties");
		p.load(fis);
	}

	@Test
	public void PostTest() throws IOException {
		
		String xmlBody = Payload.generateStringFromResource("P:\\Eclipse Workspace\\RestAssured\\src\\test\\java\\files\\Payload.xml");

		//Base URI
		RestAssured.baseURI = p.getProperty("HOST");

		//3 types of Parameters can be passed : Path, Query & Header
		//In POST requests, Path parameters cannot be passed. For Query parameter, key can be passed as part of the end point url
		//All other parameters should be passed as part of the body only

		//Provide all resources and parameters that are given
		Response res = given()
		.queryParam("key", p.getProperty("KEY"))
		.body(xmlBody).
		when()
		.post(Resources.placePostDataXML()).
		then()
		.assertThat()
		.statusCode(200)
		.and()
		.contentType(ContentType.XML)
		.extract()
		.response();
		
		XmlPath xp = Reusables.rawToXml(res);
		
		System.out.println(xp.get("PlaceAddResponse.place_id"));
		

	}

}
