package de.ait.SuperTutor.gui.tests;

import de.ait.SuperTutor.dto.AuthRequestDto;
import de.ait.SuperTutor.gui.core.ApplicationManager;
import de.ait.SuperTutor.gui.core.BaseLoginTest;
import de.ait.SuperTutor.gui.core.UIHelper;
import de.ait.SuperTutor.utils.MyDataProvider;
import de.ait.SuperTutor.utils.TokenProvider;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginUITest extends BaseLoginTest {

    @Test(dataProvider = "defaultUserData", dataProviderClass = MyDataProvider.class)
    public void loginSuccessGoogleTest(AuthRequestDto user) {

        ui = new UIHelper(app.getDriver());

        ApplicationManager.logger.info("Step 1: Открываем сайт");
        app.getDriver().get("https://11-silver.vercel.app/");

        ApplicationManager.logger.info("Step 2: Клик по иконке Sign in");
        ui.clickUniversal(null, null, null, "header nav div div button span", null, null);

        ApplicationManager.logger.info("Step 3: Ввод email и клик 'Далее'");
        ui.type("input[type='email']", user.getEmail());
        ui.clickButtonByText("Далее");

        ApplicationManager.logger.info("Step 4: Ввод пароля и клик 'Далее'");
        ui.type("input[type='password']", user.getPassword());
        ui.clickButtonByText("Далее");

        ApplicationManager.logger.info("Step 5: Проверяем появление элемента Sign out");
        Assert.assertTrue(
                ui.waitForVisibilityXPath("/html/body/header/nav/div/div[2]/div/button/span").isDisplayed(),
                "Элемент выхода (Sign out) не найден!"
        );

        ApplicationManager.logger.info("✔ loginSuccessGoogleTest завершён успешно");
    }

    @Test(groups = {"login"})
    public void loginSuccessMockTest() {

        ui = new UIHelper(app.getDriver());

        ApplicationManager.logger.info("Step 1: Открываем сайт");
        app.getDriver().get("https://11-silver.vercel.app/");

        ApplicationManager.logger.info("Step 2: Мок успешного логина через localStorage");
        ((JavascriptExecutor) app.getDriver()).executeScript(
                "window.localStorage.setItem('token', 'FAKE_TOKEN');"
        );

        app.getDriver().navigate().refresh();

        ApplicationManager.logger.info("Step 3: Проверяем элемент Sign out");
        Assert.assertTrue(
                ui.waitForVisibilityXPath("/html/body/header/nav/div/div[2]/div/button/span").isDisplayed(),
                "Элемент выхода (Sign out) не найден!"
        );

        ApplicationManager.logger.info("✔ loginSuccessMockTest завершён успешно");
    }

    @Test(dataProvider = "defaultUserData", dataProviderClass = MyDataProvider.class)
    public void loginTest(AuthRequestDto user) {

        UIHelper ui = new UIHelper(app.getDriver());

        ApplicationManager.logger.info("Step 1: Открываем сайт");
        app.getDriver().get("https://11-silver.vercel.app/");

        String realLogin = System.getProperty("realLogin", "false");

        if (Boolean.parseBoolean(realLogin)) {
            ApplicationManager.logger.info("Step 2: Реальный логин через форму");
            ui.clickUniversal(null, null, null, "header nav div div button span", null, null);
            ui.type("input[type='email']", user.getEmail());
            ui.clickButtonByText("Далее");
            ui.type("input[type='password']", user.getPassword());
            ui.clickButtonByText("Далее");
        } else {
            ApplicationManager.logger.info("Step 2: Мокнутый логин через Firebase токен");
            String token = TokenProvider.getFirebaseIdToken(user.getEmail(), user.getPassword());
            ((JavascriptExecutor) app.getDriver()).executeScript(
                    "window.localStorage.setItem('token', arguments[0]);", token
            );
            app.getDriver().navigate().refresh();
        }

        ApplicationManager.logger.info("Step 3: Проверяем элемент Sign out");
        Assert.assertTrue(
                ui.waitForVisibilityXPath("/html/body/header/nav/div/div[2]/div/button/span").isDisplayed(),
                "Элемент выхода (Sign out) не найден!"
        );

        ApplicationManager.logger.info("✔ loginTest завершён успешно");
    }
}

