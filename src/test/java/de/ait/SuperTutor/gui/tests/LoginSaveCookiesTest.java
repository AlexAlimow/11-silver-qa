package de.ait.SuperTutor.gui.tests;


import de.ait.SuperTutor.gui.core.TestBase;
import de.ait.SuperTutor.gui.core.UIHelper;
import de.ait.SuperTutor.gui.core.utils.CookieUtils;
import org.testng.annotations.Test;

public class LoginSaveCookiesTest extends TestBase {

    @Test
    public void loginAndSaveCookies() {

        UIHelper ui = new UIHelper(app.getDriver());
        app.getDriver().get("https://11-silver.vercel.app/");

        // ⚠️ Твои шаги Google входа
        ui.clickUniversal(null, null, null, "header nav div div button span", null, null);

        ui.type("input[type='email']", "gmtestacc14@gmail.com");
        ui.clickButtonByText("Далее");

        ui.type("input[type='password']", "TestTest007!");
        ui.clickButtonByText("Далее");

        // ждем что мы залогинены
        ui.waitForVisibilityXPath("/html/body/header/nav/div/div[2]/div/button/span");

        // 💾 сохраняем cookies
        CookieUtils.saveCookies(app.getDriver());
    }
}

