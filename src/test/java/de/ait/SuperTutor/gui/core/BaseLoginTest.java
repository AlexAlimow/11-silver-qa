package de.ait.SuperTutor.gui.core;


import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

public class BaseLoginTest extends TestBase {

    protected UIHelper ui;

    @BeforeMethod
    public void setUpUI() {
        ui = new UIHelper(app.getDriver());
        app.getDriver().get("https://11-silver.vercel.app/");

        // Проверяем флаг для реального логина
        String realLogin = System.getProperty("realLogin", "false");
        if (!Boolean.parseBoolean(realLogin)) {
            // ✅ Мок успешной авторизации
            ((JavascriptExecutor) app.getDriver()).executeScript(
                    "window.localStorage.setItem('token', 'FAKE_TOKEN');"
            );
            app.getDriver().navigate().refresh();
        }
    }

    protected void assertLoggedIn() {
        // Проверка, что пользователь залогинен (Sign out виден)
        ui.waitForVisibilityXPath("/html/body/header/nav/div/div[2]/div/button/span");
        Assert.assertTrue(
                ui.waitForVisibilityXPath("/html/body/header/nav/div/div[2]/div/button/span").isDisplayed(),
                "Элемент выхода (Sign out) не найден!"
        );
    }
}
