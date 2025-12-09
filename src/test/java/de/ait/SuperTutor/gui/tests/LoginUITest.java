package de.ait.SuperTutor.gui.tests;

import de.ait.SuperTutor.dto.AuthRequestDto;
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


    @Test(groups = {"login"})
    public void loginSuccessMockTest() {

        UIHelper ui = new UIHelper(app.getDriver());

        // 1. Открываем сайт
        app.getDriver().get("https://11-silver.vercel.app/");

        // 2. Вставляем токен в localStorage (мокаем успешный логин)
        ((JavascriptExecutor) app.getDriver()).executeScript(
                "window.localStorage.setItem('token', 'FAKE_TOKEN');"
        );

        // 3. Обновляем страницу, чтобы UI подхватил токен
        app.getDriver().navigate().refresh();

        // 4. Проверяем появление элемента span внутри кнопки выхода
        ui.waitForVisibilityXPath("/html/body/header/nav/div/div[2]/div/button/span");
        Assert.assertTrue(
                ui.waitForVisibilityXPath("/html/body/header/nav/div/div[2]/div/button/span").isDisplayed(),
                "Элемент выхода (Sign out) не найден!"
        );
    }

//    @Test(dataProvider = "defaultUserData", dataProviderClass = MyDataProvider.class)
//    public void loginTest(AuthRequestDto user) {
//        String realLogin = System.getProperty("realLogin", "false");
//
//        if (Boolean.parseBoolean(realLogin)) {
//            // 🔹 Реальный логин через форму
//            ui.clickUniversal(null, null, null, "svg.lucide-log-in", null, null);
//            ui.type("input[type='email']", user.getEmail());
//            ui.clickButtonByText("Далее");
//            ui.type("input[type='password']", user.getPassword());
//            ui.clickButtonByText("Далее");
//        }
//
//        //  Проверка выхода (Sign out) одинаково для мок и реального логина
//        assertLoggedIn();
//    }

    @Test(groups = {"login"}, dataProvider = "defaultUserData", dataProviderClass = MyDataProvider.class)
    public void loginTest(AuthRequestDto user) {
        ui = new UIHelper(app.getDriver());

        // Открываем сайт
        app.getDriver().get("https://11-silver.vercel.app/");

        String realLogin = System.getProperty("realLogin", "false");

        if (Boolean.parseBoolean(realLogin)) {
            // 🔹 Реальный логин через форму
            ui.clickUniversal(null, null, null, "svg.lucide-log-in", null, null);
            ui.type("input[type='email']", user.getEmail());
            ui.clickButtonByText("Далее");
            ui.type("input[type='password']", user.getPassword());
            ui.clickButtonByText("Далее");
        } else {
            // 🔹 Мокнутый логин через TokenProvider
            String token = TokenProvider.getToken(user.getEmail());
            ((JavascriptExecutor) app.getDriver()).executeScript(
                    "window.localStorage.setItem('token', arguments[0]);", token
            );
            app.getDriver().navigate().refresh();
        }

        // Проверка успешного логина — span внутри кнопки Sign out
        Assert.assertTrue(
                ui.waitForVisibilityXPath("/html/body/header/nav/div/div[2]/div/button/span").isDisplayed(),
                "Элемент выхода (Sign out) не найден!"
        );
    }
}


