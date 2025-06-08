package Day5;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ParsingXMLResponse {


    @Test(priority = 1)
    void testXMLResponse1()
    {
        given()
                .when()
                .get("https://mocktarget.apigee.net/xml")
                .then()
                .statusCode(200)
                .contentType("application/xml; charset=utf-8")
                .header("Content-Type",equalTo("application/xml; charset=utf-8"))
                .body("root.city",equalTo("San Jose"))
                .body("root.firstName",equalTo("John"))
                .body("root.lastName",equalTo("Doe"))
                .body("root.state",equalTo("CA"))
                .log().body();
    }
    @Test(priority = 2)
    void testXMLResponse2()
    {
        given()
                .when()
                .get("https://httpbin.org/xml")
                .then()
                .statusCode(200)
                .contentType("application/xml")
                .body("slideshow.@title",equalTo("Sample Slide Show"))
                .log().body();

    }

    @Test(priority = 3)
    void testXMLParsingResponse3()
    {
        Response response =given()
                .when()
                .get("https://httpbin.org/xml")
                .then()
                .statusCode(200)
                .contentType("application/xml")
                .extract().response();

        XmlPath xmlPath = new XmlPath(response.asString());
        //Number of slides and capturing tiitles of slides to get into list in XML response body
        List<String> slidetitles = xmlPath.getList("slideshow.slide.title");
         // Parsing and counting slides in XML
         assertThat(slidetitles.size(),is(2));
         assertThat(slidetitles.get(0),is("Wake up to WonderWidgets!"));
         assertThat(slidetitles.get(1),is("Overview"));
         assertThat(slidetitles,hasItems("Wake up to WonderWidgets!","Overview"));


         //Parsing Items in XML Response Body
         List<String> items = xmlPath.getList("slideshow.slide.item");
         System.out.println("Number of items:"+items.size());
         assertThat(items.size(),is(3));

         //Validate Items data
        assertThat(items.get(0),is("WonderWidgets"));
        assertThat(items.get(2),is("buys"));
        assertThat(items,hasItems("WonderWidgets","buys"));
        //check presence of item in the response
        boolean status = false;
         for( String item :items)
         {
            if(item.equals("WonderWidgets"))
            {
                status =true;
                break;
            }
         }
         assertThat(status,is(true) );

    }
}
