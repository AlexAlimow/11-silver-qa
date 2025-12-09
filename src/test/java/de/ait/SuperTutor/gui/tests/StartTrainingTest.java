package de.ait.SuperTutor.gui.tests;

import de.ait.SuperTutor.gui.core.BaseLoginTest;
import de.ait.SuperTutor.gui.core.UIHelper;
import de.ait.SuperTutor.gui.core.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StartTrainingTest extends BaseLoginTest {

    @Test(groups = {"regression"})
    public void TC03_startTrainingNavigation() {

        ui = new UIHelper(app.getDriver());

        // 1. Домашняя страница уже открыта и пользователь залогинен через BaseLoginTest
        ApplicationManager.logger.info("Step 1: Домашняя страница открыта и пользователь залогинен");

        // 2. Нажимаем кнопку Start Training
        ApplicationManager.logger.info("Step 2: Нажимаем кнопку 'Start Training'");
        ui.waitForVisibility("a[href='/decks']").click();
        ApplicationManager.logger.info("✔ Кнопка 'Start Training' нажата");

        // 3. Проверяем, что произошел переход на страницу /decks
        ApplicationManager.logger.info("Step 3: Проверяем, что произошел переход на страницу /decks");
        ui.waitForUrl("/decks");
        ApplicationManager.logger.info("✔ Переход на /decks подтверждён");

        // 4. Проверяем наличие заголовка на странице Decks
        ApplicationManager.logger.info("Step 4: Проверяем наличие заголовка на странице Decks");
        Assert.assertTrue(
                ui.waitForVisibilityXPath("//h2[contains(text(),'Select a deck and tap it to begin training')]").isDisplayed(),
                "Заголовок страницы Decks не найден!"
        );
        ApplicationManager.logger.info("✔ Заголовок страницы Decks найден");

        ApplicationManager.logger.info("TC03_startTrainingNavigation завершён успешно");
    }
}
