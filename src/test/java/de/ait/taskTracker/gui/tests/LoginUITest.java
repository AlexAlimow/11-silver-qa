package de.ait.taskTracker.gui.tests;

import de.ait.taskTracker.dto.AuthRequestDto;
import de.ait.taskTracker.gui.core.TestBase;
import de.ait.taskTracker.gui.core.UIHelper;
import de.ait.taskTracker.utils.MyDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginUITest extends TestBase {

    @Test(dataProvider = "defaultUserData", dataProviderClass = MyDataProvider.class)
    public void loginSuccessTest(AuthRequestDto user) {

        UIHelper ui = new UIHelper(app.getDriver());

        // 1. Открываем сайт
        app.getDriver().get("https://11-silver.vercel.app/");

        // 2. Клик по иконке Log in
        ui.clickUniversal(null, null, null, "svg.lucide-log-in", null, null);

        // 3. Ввод email и клик "Далее"
        ui.type("input[type='email']", user.getEmail());
        ui.clickButtonByText("Далее");

        // 4. Ввод пароля и клик "Далее"
        ui.type("input[type='password']", user.getPassword());
        ui.clickButtonByText("Далее");

        // 5. Проверяем появление элемента span внутри кнопки выхода
        ui.waitForVisibilityXPath("/html/body/header/nav/div/div[2]/div/button/span");
        Assert.assertTrue(
                ui.waitForVisibilityXPath("/html/body/header/nav/div/div[2]/div/button/span").isDisplayed(),
                "Элемент выхода (Sign out) не найден!"
        );
    }
}
