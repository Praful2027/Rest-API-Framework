package jira;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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

		String sessionPayload = "{" + 
				"\"username\": \""+p.getProperty("USER")+"\"," + 
				"\"password\": \""+p.getProperty("PWD")+"\"" + 
				"}";
		
		String issuePayload = "{" + 
				"\"fields\": {" + 
				"\"project\": {" + 
				"\"key\": \"AT\"" + 
				"}," + 
				"\"summary\": \"Rest Assured Bug\"," + 
				"\"description\": \"First Rest Assured Bug created\"," + 
				"\"issuetype\": {" + 
				"\"name\": \"Bug\"" + 
				"}" + 
				"}" + 
				"}";
		
		String commentPayload = "{" + 
				"      \"body\": \"First Comment\"," + 
				"      \"visibility\": {" + 
				"        \"type\": \"role\"," + 
				"        \"value\": \"Administrators\"" + 
				"}";
		

		//Create a session
		Response res = given()
				.contentType(ContentType.JSON)
				.body(sessionPayload).
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

		JsonPath js = new JsonPath(resStr);
		String sessionName = js.get("session.name");
		String sessionValue = js.get("session.value");
		
		sessionID = sessionName+"="+sessionValue;
		System.out.println(sessionID);
		
		//Create Issue
		
		Response res1 = given()
				.contentType(ContentType.JSON)
				.header("Cookie",sessionID)
				.body(issuePayload).
			when()
				.post(p.getProperty("CREATEISSUE")).
			then()
				.assertThat()
				.statusCode(201).
			extract()
				.response();
				
		//Extract Issue ID and Key
		String issIdStr = res1.asString();
		System.out.println(issIdStr);
		
		JsonPath jsId = new JsonPath(issIdStr);
		String issueId = jsId.get("id");
		String issueKey = jsId.get("key");
		
		System.out.println("Ïssue Id: "+issueId+" | Issue Key: "+issueKey);
		
		//Add a comment
		Response res2 = given()
				.contentType(ContentType.JSON)
				.header("Cookie",sessionID)
				.body(commentPayload).
			when()
				.post(p.getProperty("ÄDDCOMMENT").replaceAll("{issueIdOrKey}", issueId)).
			then()
				.assertThat()
				.statusCode(200).
			extract()
				.response();
		
		String commentStr = res2.asString();
		System.out.println(commentStr);
		
		JsonPath jsComId = new JsonPath(commentStr);
		String comID = jsComId.get("id");
		
		//Update a comment
		given()
			.contentType(ContentType.JSON)
			.header("Cookie",sessionID)
			.body(commentPayload).
		when()
			.post(p.getProperty("ÄDDCOMMENT").replaceAll("{issueIdOrKey}", issueId)).
		then()
			.assertThat()
			.statusCode(200);
		
		
		//Delete an issue
		given()
			.contentType(ContentType.JSON)
			.header("Cookie",sessionID)
			.body(commentPayload).
		when()
			.post(p.getProperty("DELETECOMMENT").replaceAll("{issueIdOrKey}", issueId)).
		then()
			.assertThat()
			.statusCode(204);

	}
}
