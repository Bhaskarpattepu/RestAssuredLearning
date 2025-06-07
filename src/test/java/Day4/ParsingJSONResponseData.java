package Day4;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ParsingJSONResponseData {

    @Test(priority = 1)
    void testJsonResponse() {

        //Aproach1

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:3000/store")
                .then()
                .statusCode(200)
                .header("Content-Type","application/json")
                .body("book[4].title",equalTo("You Don’t Know JS"));


        //Aproach 2

        Response res = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:3000/store");

        Assert.assertEquals(res.getStatusCode(),200); //Validation 1
        Assert.assertEquals(res.header("Content-Type"),"application/json");
        String bookname = res.jsonPath().get("book[4].title").toString();
        Assert.assertEquals(bookname,"You Don’t Know JS");


    }

    @Test(priority = 2)
    void testJsonResponseBodyData()
    {
        Response res =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("http://localhost:3000/store");
        /*
        Assert.assertEquals(res.getStatusCode(),200); //Validation 1
        Assert.assertEquals(res.header("Content-Type"),"application/json");
        String bookname = res.jsonPath().get("book[4].title").toString();
        Assert.assertEquals(bookname,"You Don’t Know JS");

         */

        //JSONObject Class
        //This approach is used when developer changes in array order then our script fails so not to fail in those case
        JSONObject jo = new JSONObject(res.asString());//here we not pass res directly first we have to convert to String
        for(int i=0;i<jo.getJSONArray("book").length();i++)
        {
            String bookTittle = jo.getJSONArray("book").getJSONObject(i).get("title").toString();
            System.out.println(bookTittle);
        }

        boolean status=false;
        for(int i=0;i<jo.getJSONArray("book").length();i++)
        {
            String bookTittle = jo.getJSONArray("book").getJSONObject(i).get("title").toString();
            if(bookTittle.equalsIgnoreCase("Introduction to Algorithms"))
            {

                status = true;
                break;

            }
        }
        Assert.assertTrue(status);

    //Validate total price of books
     double totalprice=0;

        for(int i=0;i<jo.getJSONArray("book").length();i++)
        {
            String price = jo.getJSONArray("book").getJSONObject(i).get("price").toString();
            totalprice=totalprice+Double.parseDouble(price);
        }
        System.out.println(totalprice);

        Assert.assertEquals(totalprice,2430.49 );

    }
}
