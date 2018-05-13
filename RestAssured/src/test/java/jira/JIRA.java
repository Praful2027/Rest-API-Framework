package jira;

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

public class JIRA {

	Properties p = new Properties();
	static String sessionID;

	@BeforeTest
	public void getData() throws IOException {

		FileInputStream fis = new FileInputStream("P:\\GitRepository\\RestAssured\\src\\test\\java\\jira\\jira.properties");
		p.load(fis);
	}

	@Test
	public void JiraAPI() {

		RestAssured.baseURI = p.getProperty("HOST");

		String payload = "{" + 
				"\"username\": \""+p.getProperty("USER")+"\"," + 
				"\"password\": \""+p.getProperty("PWD")+"\"" + 
				"}";

		//Create a session
		Response res = given()
				.contentType(ContentType.JSON)
				.body(payload).
				when()
				.post(p.getProperty("LOGIN")).
				then()
				.assertThat()
				.statusCode(200).
				extract()
				.response();

		//Extract session id
		String resStr = res.asString();
		System.out.println(resStr);

		//Pick the place_id from the response body
		JsonPath js = new JsonPath(resStr);
		String sessionName = js.get("session.name");
		String sessionValue = js.get("session.value");
		
		sessionID = sessionName+"="+sessionValue;
		System.out.println(sessionID);
		
		


	}
}
