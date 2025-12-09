package de.ait.SuperTutor.api.tests;

import de.ait.SuperTutor.api.config.TestBase;
import de.ait.SuperTutor.dto.AuthResponseDto;
import org.testng.annotations.Test;

import static de.ait.SuperTutor.config.AppConfigApi.getProperty;
import static de.ait.SuperTutor.config.AppConfigApi.softly;
import static de.ait.SuperTutor.api.endpoints.AuthApi.getAuthResponse;

public class LoginTests extends TestBase {

    @Test(description = "Check User can authenticate with correct data and get access token")
    public void authSuccessTest() {

        logger.info("Authentication with email - {} and password - {} ", getProperty("default.email"), getProperty("default.password"));

        AuthResponseDto responseDto = getAuthResponse()
                .assertThat().statusCode(200)
                .extract().body().as(AuthResponseDto.class);

        softly.assertNotNull(responseDto.getAccessToken(), "accessToken is null");
        softly.assertFalse(responseDto.getAccessToken().isEmpty(), "accessToken is empty");
        softly.assertAll();

        System.out.println(responseDto.getAccessToken());
        System.out.println(responseDto.getRefreshToken());
    }

}
