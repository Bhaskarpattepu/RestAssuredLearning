package Day3;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class LoggingDemo {

    @Test
    void testLogs()
    {
        given()
                .when()
                .get("https://www.google.com/")
                .then()
                //.log().body();
                //.log().headers();
                //.log().cookies();
                .log().all();

    }

}
