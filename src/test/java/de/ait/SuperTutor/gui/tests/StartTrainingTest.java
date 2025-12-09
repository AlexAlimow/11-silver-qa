package de.ait.SuperTutor.gui.tests;

import de.ait.SuperTutor.gui.core.BaseLoginTest;
import de.ait.SuperTutor.gui.core.UIHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StartTrainingTest extends BaseLoginTest {


    @Test
    public void TC03_startTrainingNavigation() {

        ui = new UIHelper(app.getDriver());

        // 1. Домашняя страница уже открыта и пользователь залогинен через BaseLoginTest

        // 2. Нажимаем кнопку Start Training
        ui.waitForVisibility("a[href='/decks']").click();

        // 3. Проверяем, что произошел переход на страницу /decks
        ui.waitForUrl("/decks");

        // 4. Проверяем наличие заголовка на странице Decks
        Assert.assertTrue(
                ui.waitForVisibilityXPath("//h2[contains(text(),'Select a deck and tap it to begin training')]").isDisplayed(),
                "Заголовок страницы Decks не найден!"
        );
    }
}