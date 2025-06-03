package Day1;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import java.util.HashMap;



/*
given()

when()

then()
        Validate Status code ,extract response ,extract headers cookies & response body..........
 */


public class HTTPRequest {

    int id;
    @Test(priority = 1)
    void getUsers()
    {
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")

                .then()
                .statusCode(200)
                .body("page",equalTo(2))
                .log().all();



    }

    @Test(priority = 2)
    void createUser()
    {
        HashMap data = new HashMap();
        data.put("name","pavan");
        data.put("job","trainer");

        given()
                .contentType("application/json")
                .body(data)
                .header("x-api-key", "reqres-free-v1")
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .log().all();
    }

@Test(priority = 3)
    void createuser1()
    {

        HashMap data = new HashMap();
        data.put("name","pavan1");
        data.put("job","trainer1");

        id = given()
                .contentType("application/json")
                .body(data)
                .header("x-api-key", "reqres-free-v1")
                .when()
                .post("https://reqres.in/api/users")
                .jsonPath().getInt("id");

               // .then()
                //.statusCode(201)
                //.log().all();

    }

    @Test(priority = 4,dependsOnMethods = {"createuser1"})
    void updateUser()
    {
        HashMap data = new HashMap();
        data.put("name","Bhaskar");
        data.put("job","teacher");

        given()
                .contentType("application/json")
                .body(data)
                .header("x-api-key", "reqres-free-v1")
                .when()
                .put("https://reqres.in/api/users/"+id)
                .then()
        .statusCode(200)
        .log().all();

    }

    @Test(priority = 5)
    void deleteUser()
    {
        given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .delete("https://reqres.in/api/users/"+id)
                .then()
                .statusCode(204)
                .log().all();
    }


}
