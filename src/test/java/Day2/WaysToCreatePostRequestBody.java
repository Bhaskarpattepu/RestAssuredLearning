package Day2;


/*
Different ways to create post request body/ request payload.
---------------
1) HashMap
2) org.json library
3) Using java POJO class
4) External json file

 */

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class WaysToCreatePostRequestBody {

    //post request body using hashmaop
    String id;
    @Test(priority = 1)
    void testPostusingHashmap()
    {

        HashMap data = new HashMap();
        data.put("name","Scott");
        data.put("location","France");
        data.put("phone","123456");

        String courseArr[] = {"C","C++"};
         data.put("courses",courseArr);


        id= given()
                 .contentType("application/json")
                 .body(data)
                 .when()
                        .post("http://localhost:3000/students")
                 .then()
                     .statusCode(201)
                     .body("name",equalTo("Scott"))
                     .body("location",equalTo("France"))
                     .body("phone",equalTo("123456"))
                     .body("courses[0]",equalTo("C"))
                     .body("courses[1]",equalTo("C++"))
                     .header("Content-Type","application/json")
                     .log().all()
                     .extract().path("id");

    }

    @Test(priority = 2 , dependsOnMethods = {"testPostusingHashmap"})
    void testDelete()
    {
        given()
                .when()
                .delete("http://localhost:3000/students/"+id)
                .then()
                .statusCode(200);
    }


@Test(priority = 3)
    void testPostUsingJson()
    {
        JSONObject data = new JSONObject();

        data.put("name","Scott");
        data.put("location","France");
        data.put("phone","123456");
        String coursesArr[]={"C","C++"};
        data.put("courses",coursesArr);

        id= given()
                .contentType("application/json")
                .body(data.toString())
                .when()
                .post("http://localhost:3000/students")
                .then()
                .statusCode(201)
                .body("name",equalTo("Scott"))
                .body("location",equalTo("France"))
                .body("phone",equalTo("123456"))
                .body("courses[0]",equalTo("C"))
                .body("courses[1]",equalTo("C++"))
                .header("Content-Type","application/json")
                .log().all()
                .extract().path("id");

    }

    @Test(priority = 4,dependsOnMethods = {"testPostUsingJson"})
    void testDeleteJSon()
    {
        given()
                .when()
                .delete("http://localhost:3000/students/"+id)
                .then()
                .statusCode(200);
    }

    @Test(priority = 5)
    void testPostUsingPojo()
    {
            Pojo_PostRequest data = new Pojo_PostRequest();
            data.setName("Scott");
            data.setLocation("France");
            data.setPhone("123456");
            String[] coursesArr = {"C","C++"};
            data.setCourses(coursesArr);

        id= given()
                .contentType("application/json")
                .body(data)
                .when()
                .post("http://localhost:3000/students")
                .then()
                .statusCode(201)
                .body("name",equalTo("Scott"))
                .body("location",equalTo("France"))
                .body("phone",equalTo("123456"))
                .body("courses[0]",equalTo("C"))
                .body("courses[1]",equalTo("C++"))
                .header("Content-Type","application/json")
                .log().all()
                .extract().path("id");
    }

    @Test(priority = 6,dependsOnMethods = {"testPostUsingPojo"})
    void testDeletePjo()
    {
        given()
                .when()
                .delete("http://localhost:3000/students/"+id)
                .then()
                .statusCode(200);
    }
@Test(priority = 7)
void testPostusingExternalJsonFile() throws FileNotFoundException {
    File f = new File(".\\Body.json");
    FileReader fr = new FileReader(f);
    JSONTokener jt = new JSONTokener(fr);
    JSONObject data = new JSONObject(jt);

    id= given()
            .contentType("application/json")
            .body(data.toString())
            .when()
            .post("http://localhost:3000/students")
            .then()
            .statusCode(201)
            .body("name",equalTo("Scott"))
            .body("location",equalTo("France"))
            .body("phone",equalTo("123456"))
            .body("courses[0]",equalTo("C"))
            .body("courses[1]",equalTo("C++"))
            .header("Content-Type","application/json")
            .log().all()
            .extract().path("id");
}

    @Test(priority = 8,dependsOnMethods = {"testPostusingExternalJsonFile"})
    void testDeleteExternalJSonFile()
    {
        given()
                .when()
                .delete("http://localhost:3000/students/"+id)
                .then()
                .statusCode(200);
    }

}
