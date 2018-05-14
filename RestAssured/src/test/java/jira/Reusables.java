package jira;

import static io.restassured.RestAssured.given;

import java.util.Properties;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Reusables {
	
	public static String getSessionKey(String payload, Properties p) {
		String sID;
		
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
		
		sID = sessionName+"="+sessionValue;
		System.out.println(sID);
		return sID;
	}

}
