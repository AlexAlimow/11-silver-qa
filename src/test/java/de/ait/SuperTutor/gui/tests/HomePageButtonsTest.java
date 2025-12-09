package de.ait.SuperTutor.gui.tests;

import de.ait.SuperTutor.gui.core.ApplicationManager;
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
        ApplicationManager.logger.info("Step 1: Открываем домашнюю страницу https://11-silver.vercel.app/");
        app.getDriver().get("https://11-silver.vercel.app/");

        // 2. Проверяем кнопку "Sign in" в хедере
        ApplicationManager.logger.info("Step 2: Проверяем кнопку 'Sign in' в хедере");
        Assert.assertTrue(
                ui.waitForVisibility("header nav div div button span").isDisplayed(),
                "Кнопка 'Sign in' в хедере не видна!"
        );
        ApplicationManager.logger.info("✔ Кнопка 'Sign in' в хедере видна");

        // 3. Проверяем кнопку "Sign in" в main
        ApplicationManager.logger.info("Step 3: Проверяем кнопку 'Sign in' в main");
        Assert.assertTrue(
                ui.waitForVisibility("main div div div button span").isDisplayed(),
                "Кнопка 'Sign in' в main не видна!"
        );
        ApplicationManager.logger.info("✔ Кнопка 'Sign in' в main видна");

        // 4. Проверяем кнопку "Start Training"
        ApplicationManager.logger.info("Step 4: Проверяем кнопку 'Start Training'");
        Assert.assertTrue(
                ui.waitForVisibility("a[href='/decks']").isDisplayed(),
                "Кнопка 'Start Training' не видна на главной странице!"
        );
        ApplicationManager.logger.info("✔ Кнопка 'Start Training' видна на главной странице");

        ApplicationManager.logger.info("TC01_checkHomePageButtons завершён успешно");
    }
}
