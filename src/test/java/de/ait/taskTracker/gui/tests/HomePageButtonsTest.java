package de.ait.taskTracker.gui.tests;

import de.ait.taskTracker.gui.core.BaseLoginTest;
import de.ait.taskTracker.gui.core.UIHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageButtonsTest extends BaseLoginTest {

    private UIHelper ui;

    @Test
    public void TC01_checkHomePageButtons() {
        ui = new UIHelper(app.getDriver());

        // 1. Открываем домашнюю страницу
        app.getDriver().get("https://11-silver.vercel.app/");

        // 2. Проверяем кнопку "Login" в хедере
        Assert.assertTrue(
                ui.waitForVisibility("header nav svg.lucide-log-in").isDisplayed(),
                "Кнопка 'Login' в хедере не видна!"
        );

        // 3. Проверяем кнопку "Start Training"
        Assert.assertTrue(
                ui.waitForVisibility("a[href='/decks']").isDisplayed(),
                "Кнопка 'Start Training' не видна на главной странице!"
        );
    }

}
