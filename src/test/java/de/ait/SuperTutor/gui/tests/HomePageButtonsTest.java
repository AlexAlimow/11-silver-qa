package de.ait.SuperTutor.gui.tests;

import de.ait.SuperTutor.gui.core.BaseLoginTest;
import de.ait.SuperTutor.gui.core.UIHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageButtonsTest extends BaseLoginTest {

    private UIHelper ui;

    @Test(groups = {"regression"})
    public void TC01_checkHomePageButtons() {
        ui = new UIHelper(app.getDriver());

        // 1. Открываем домашнюю страницу
        app.getDriver().get("https://11-silver.vercel.app/");

        // 2. Проверяем кнопку "Sign in" в хедере
        Assert.assertTrue(
                ui.waitForVisibility("header nav div div button span").isDisplayed(),
                "Кнопка 'Sign in' в хедере не видна!"
        );

        // 3. Проверяем кнопку "Sign in" в main
        Assert.assertTrue(
                ui.waitForVisibility("main div div div button span").isDisplayed(),
                "Кнопка 'Sign in' в main не видна!"
        );

        // 4. Проверяем кнопку "Start Training"
        Assert.assertTrue(
                ui.waitForVisibility("a[href='/decks']").isDisplayed(),
                "Кнопка 'Start Training' не видна на главной странице!"
        );
    }

}
