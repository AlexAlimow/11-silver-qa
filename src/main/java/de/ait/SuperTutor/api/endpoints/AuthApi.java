package de.ait.SuperTutor.api.endpoints;

import de.ait.SuperTutor.dto.AuthRequestDto;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static de.ait.SuperTutor.config.AppConfigApi.getProperty;
import static de.ait.SuperTutor.api.objects.ObjectsData.requestDto;
import static io.restassured.RestAssured.given;

//the class stores different methods for requests
public class AuthApi {

    public static ValidatableResponse getRegistrationResponse(AuthRequestDto requestDto) {
        return given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post(getProperty("register.path"))
                .then();
    }

    public static ValidatableResponse getConfirmResponse(String confirmCode) {
        return given()
                .get(getProperty("confirm.path") + confirmCode)
                .then();
    }

    public static ValidatableResponse getAuthResponse() {
        return given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post(getProperty("login.path"))
                .then();
    }
}
