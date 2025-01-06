package GitAndRest.RestAssured2025;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import payloads.RequestPayloads;

public class ComplexJsonParse {
	
	JsonPath js;
	int ccount;
  @Test
  public void complexJsonTest() {
	  js = new JsonPath(RequestPayloads.parseComplexJson());
	  ccount = js.getInt("courses.size()");
	  System.out.println("Number of courses offered "+ ccount);
	  System.out.println("Printing title of the first course"+ js.get("courses[0].title"));
	  System.out.println("All course titles and prices below ");
	  for(int i=0;i<ccount;i++) {
		  System.out.println(js.get("courses["+i+"].title").toString());
		  if(js.getString("courses["+i+"].title").equalsIgnoreCase("RPA")) {
			  System.out.println("Price for RPA course is "+ js.getInt("courses["+i+"].price"));
			  System.out.println("Number of copies sold for RPA course are "+ js.get("courses["+i+"].copies"));
			  break;
		  }
		  System.out.println(js.getInt("courses["+i+"].price"));
		  
	  }
	  int sum = 0;
	  for(int i=0;i<ccount;i++) {
	  sum = sum + (js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies"));
	  }
	  System.out.println(sum);
	  int purchaseamt = js.get("dashboard.purchaseAmount");
	 // System.out.println("purchaseamt is "+ purchaseamt);
	  Assert.assertEquals(sum, purchaseamt);
	 
  }
}
