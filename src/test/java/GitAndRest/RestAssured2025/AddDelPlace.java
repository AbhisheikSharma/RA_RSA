package GitAndRest.RestAssured2025;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payloads.RequestPayloads;

public class AddDelPlace {

	public static void main(String[] args) {
		String pl_id;
		JsonPath js;
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		System.out.println("One");
		
		String postResponse = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(RequestPayloads.postPayload())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("status", equalTo("OK")).header("server", "Apache/2.4.52 (Ubuntu)"
				+ "").extract().response().asString();
		js = new JsonPath(postResponse);
		pl_id = js.getString("place_id");
		System.out.println("The value of place id is "+ pl_id);
		
		System.out.println("==================END OF POST TEST======================");
		
		given().header("Content-Type","application/json").queryParam("key","qaclick123").queryParam("place_id", pl_id).body(RequestPayloads.putPayload("Singapore",pl_id))
		.when().put("/maps/api/place/update/json") 
		.then().log().all().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));
		
		System.out.println("=======================END OF PUT TEST==========================");
		
		String getResp = given().queryParam("key", "qaclick123").queryParam("place_id", pl_id)
		.when().get("/maps/api/place/get/json") 
		.then().log().all().assertThat().statusCode(200).extract().body().asString();
		System.out.println("Response of Get call "+ getResp);
		js = new JsonPath(getResp);
		System.out.println("Address present in the get response is:"+ js.getString("address"));
		
		System.out.println("=======================END OF GET TEST==========================");

	}

}
