package de.ait.SuperTutor.gui.tests;

import de.ait.SuperTutor.gui.core.ApplicationManager;
import de.ait.SuperTutor.gui.core.UIHelper;
import de.ait.SuperTutor.gui.core.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class FlipCardTest extends TestBase {

    @Test
    public void TC04_checkFlipCardFunctionalitySoftAssert() {

        ApplicationManager.logger.info("=== TC04: Проверка переворота карточки с SoftAssert ===");

        UIHelper ui = new UIHelper(app.driver);


        ApplicationManager.logger.info("Открываем домашнюю страницу");
        app.driver.get("https://11-silver.vercel.app/");

        ApplicationManager.logger.info("Кликаем по кнопке 'Start Training'");
        WebElement decksButton = ui.waitForVisibility("a[href='/decks']");
        decksButton.click();

        ApplicationManager.logger.info("Кликаем по первой колоде");
        WebElement firstDeck = ui.waitForVisibilityXPath("/html/body/main/div[1]/div/div[2]/div[1]/div");
        firstDeck.click();

        ApplicationManager.logger.info("Ждём контейнер карточек");
        ui.waitForVisibility("div[class*='cardContainer']");

        ApplicationManager.logger.info("Ждём лицевую сторону карточки");
        WebElement frontSpan = ui.waitForVisibility("div[class*='cardInner'] span");

        String frontText = frontSpan.getText();
        ApplicationManager.logger.info("Текст на лицевой стороне: " + frontText);
        app.softly.assertFalse(frontText.isEmpty(), "Front text не должен быть пустым");


        ApplicationManager.logger.info("Кликаем по карточке для переворота");
        frontSpan.click();

        ApplicationManager.logger.info("Ждём текст на обратной стороне");
        WebElement backSpan = ui.waitForVisibility("div[class*='cardBack'] span");

        String backText = backSpan.getText();
        ApplicationManager.logger.info("Текст на обратной стороне: " + backText);
        app.softly.assertFalse(backText.isEmpty(), "Back text не должен быть пустым");

        app.softly.assertNotEquals(frontText, backText, "Front и Back text должны различаться");

        app.softly.assertAll();
    }
}
