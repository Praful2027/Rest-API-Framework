package files;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class Reusables {

	public static XmlPath rawToXml(Response response) {

		String resStr = response.asString();
		XmlPath xp = new XmlPath(resStr);
		return xp;
	}

	public static JsonPath rawToJson(Response response) {

		String resStr = response.asString();
		JsonPath jp = new JsonPath(resStr);
		return jp;
	}

	/*public static JsonPath rawToContent(Response response, String contentType) {

		if (contentType.equalsIgnoreCase("XML")) {
			String resStr = response.asString();
			XmlPath xp = new XmlPath(resStr);
			return xp;
		}else if(contentType.equalsIgnoreCase("JSON")) {

			String resStr = response.asString();
			JsonPath jp = new JsonPath(resStr);
			return jp;
		}
	}*/ //Cannot return two types

}
