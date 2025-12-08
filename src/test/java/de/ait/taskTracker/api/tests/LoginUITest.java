package de.ait.taskTracker.api.tests;

import de.ait.taskTracker.dto.AuthRequestDto;
import de.ait.taskTracker.gui.core.TestBase;
import de.ait.taskTracker.gui.core.UIHelper;
import de.ait.taskTracker.utils.MyDataProvider;
import de.ait.taskTracker.utils.RetryAnalyser;
import org.testng.annotations.Test;

public class LoginUITest extends TestBase {

    @Test(dataProvider = "defaultUserData", dataProviderClass = MyDataProvider.class, retryAnalyzer = RetryAnalyser.class)
    public void loginSuccessTest(AuthRequestDto user) {
        UIHelper ui = new UIHelper(app.getDriver());

        app.getDriver().get("http://localhost:3000/signin");

        ui.click("svg.lucide-log-in");               // клик по иконке Log in
        ui.type("input[type='email']", user.getEmail());
        ui.clickButtonByText("Далее");

        // ui.type("input[type='password']", user.getPassword());
        // ui.click("button[type='submit']");       // кнопка Sign in

        ui.waitForUrl("/dashboard");                // проверка успешного входа
    }
}
