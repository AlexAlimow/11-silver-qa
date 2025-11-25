package de.ait.taskTracker.api.tests;

import de.ait.taskTracker.api.config.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static de.ait.taskTracker.api.endpoints.AuthApi.getAuthResponse;
import static de.ait.taskTracker.config.AppConfigApi.getProperty;
import static io.restassured.RestAssured.given;

public class GetAllProjectsTests extends TestBase {

    String token;
    String anotherToken;

    @BeforeMethod
    public void authPrecondition() {
        String accessToken = getAuthResponse()
                .extract().path("accessToken");

        String refreshToken = getAuthResponse()
                .extract().path("refreshToken");


        token = accessToken;
        anotherToken = refreshToken;
    }

    @Test
    public void getAllProjectsSuccessTest() {

        Map<String, String> cookies = new HashMap<>();
        cookies.put("Access-Token", token);
        cookies.put("Refresh-Token", anotherToken);

        given()
                .cookies(cookies)
                .get(getProperty("allProjects.path"))
                .then()
                .statusCode(200);
    }
}
