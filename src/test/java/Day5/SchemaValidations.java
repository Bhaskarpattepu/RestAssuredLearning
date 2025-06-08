package Day5;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class SchemaValidations {


    @Test(priority = 1)
    void testJsonSchema()
    {
        given()
                .when()
                .get("https://mocktarget.apigee.net/json")
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchema.json"));
    }

    @Test(priority = 2)
    void testxmlSchema()
    {
        given()
                .when()
                .get("https://mocktarget.apigee.net/xml")
                .then()
                .body(RestAssuredMatchers.matchesXsdInClasspath("xmlSchema.xsd "));
    }
}
