package de.ait.taskTracker.api.tests;

import de.ait.taskTracker.api.config.TestBase;
import de.ait.taskTracker.dataBase.DbData;
import de.ait.taskTracker.dataBase.DbUtils;
import de.ait.taskTracker.dto.AuthRequestDto;
import de.ait.taskTracker.utils.MyDataProvider;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static de.ait.taskTracker.api.endpoints.AuthApi.getConfirmResponse;
import static de.ait.taskTracker.api.endpoints.AuthApi.getRegistrationResponse;
import static org.hamcrest.Matchers.equalTo;

public class RegistrationTests extends TestBase {

    String userEmail;

    @Test(description = "Check User can create new account registering with positive data and conforming registration with confirm code",
            dataProviderClass = MyDataProvider.class,dataProvider = "createNewUserAPI")
    public void registrationSuccessTest(AuthRequestDto requestDto) {

        //create new user
        String userId = getRegistrationResponse(requestDto)
                .assertThat().statusCode(201)
                        .extract().path("id");

        logger.info("Assert checks status code is 201");

        //create MySql query with specific user_id
        String query = String.format(DbData.GET_CONFIRM_CODE_TEMPLATE, userId);

        //fetch confirmCode from DB
        String confirmCode = DbUtils.getDataFromTable(query,"formatted_id");

        //confirm registration
        String email = getConfirmResponse(confirmCode)
                .assertThat().statusCode(200)
                .assertThat().body("confirmationStatus", equalTo("CONFIRMED"))
                .extract().path("email");

        logger.info("Assert checks status code is 200 and registration confirmed");
        //fetch email variable
        userEmail = email;
    }

    @AfterMethod(description = "Clear DB after executing test")
    public void postCondition() {
        //delete created user
        String query = String.format(DbData.DELETE_USER, userEmail);
        DbUtils.deleteRowFromTable(query);
        logger.info("Account was deleted");
    }

}
