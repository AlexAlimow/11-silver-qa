package de.ait.SuperTutor.gui.tests;


import de.ait.SuperTutor.gui.core.TestBase;
import de.ait.SuperTutor.gui.core.UIHelper;
import de.ait.SuperTutor.gui.core.utils.CookieUtils;
import org.testng.annotations.Test;

public class LoginViaCookiesTest extends TestBase {

    @Test
    public void loginUsingSavedCookies() {

        UIHelper ui = new UIHelper(app.getDriver());

        app.getDriver().get("https://11-silver.vercel.app/");

        // ⚡ загружаем cookies
        CookieUtils.loadCookies(app.getDriver());

        // обновляем чтобы cookies применились
        app.getDriver().navigate().refresh();

        // проверяем что мы залогинены
        ui.waitForVisibilityXPath("/html/body/header/nav/div/div[2]/div/button/span");

        // идём в decks
        ui.clickByXpath("/html/body/header/nav/div/div[1]/a[2]");

        // жмем Create
        ui.clickByXpath("/html/body/main/div[1]/div/div[3]/div/button");

        app.logger.info("✔ Авторизация через cookies работает");
    }
}