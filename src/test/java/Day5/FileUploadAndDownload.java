package Day5;

import org.testng.annotations.Test;


import java.io.File;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
public class FileUploadAndDownload {

    @Test(priority = 1)
    void SingleFileUpload()
    {
        File myfile = new File("D:\\Selenium Hybrid FrameWork\\RestAssured\\RestAssuredSetup\\uploadingfile1.txt");


        given()
                .multiPart("file",myfile)
                .contentType("multipart/form-data")
                .when()
            .post("http://localhost:8080/uploadFile")
            .then()
            .statusCode(200)
                .body("fileName",equalTo("uploadingfile1.txt"))
                .log().all();
    }

    @Test(priority = 2)
    void multipleFilesUpload()
    {
        File myfile1 = new File("D:\\Selenium Hybrid FrameWork\\RestAssured\\RestAssuredSetup\\uploadingfile1.txt");
        File myfile2 = new File("D:\\Selenium Hybrid FrameWork\\RestAssured\\RestAssuredSetup\\uploadingfile2.txt");


        given()
                .multiPart("files",myfile1)
                .multiPart("files",myfile2)
                .contentType("multipart/form-data")
                .when()
                .post("http://localhost:8080/uploadMultipleFiles")
                .then()
                .statusCode(200)
                .body("[0].fileName",equalTo("uploadingfile1.txt"))
                .body("[1].fileName",equalTo("uploadingfile2.txt"))
                .log().all();
    }

    @Test(priority = 3)
    void fileupload()
    {
        given()
                .when()
                .get("http://localhost:8080/downloadFile/uploadingfile1.txt")
                .then()
                .statusCode(200);
    }

}
