package Day3;

import io.restassured.response.Response;
import org.hamcrest.Description;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
public class CookiesDemo {

    @Test(priority = 1)
    void testCookies()
    {
        when()
                .get("https://www.google.com/")
        .then()
                .cookie("AEC")
            .log().all();
    }

    @Test(priority = 2)
    void getCookiesInfo()
    {

        Response res =given()
                .when()
                .get("https://www.google.com/");

        //Caturing Single Cookie Info
       String cookie_value = res.getCookie("AEC");
       System.out.println("The Value of AEC Cookie is =====> "+cookie_value);

       //get all cookies
        Map<String ,String> cookie_values = res.getCookies();
       //System.out.println(cookie_values.keySet());
        for (String k:cookie_values.keySet())
        {
           String cookie_valueofeachcookie = res.getCookie(k);
           System.out.println(k +" cookie value is" + cookie_valueofeachcookie);
        }


    }
}
