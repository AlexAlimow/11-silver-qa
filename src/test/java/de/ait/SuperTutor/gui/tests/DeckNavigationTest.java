package de.ait.SuperTutor.gui.tests;

import de.ait.SuperTutor.gui.core.ApplicationManager;
import de.ait.SuperTutor.gui.core.TestBase;
import de.ait.SuperTutor.gui.core.UIHelper;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class DeckNavigationTest extends TestBase {
    @Test(groups = {"regression"})
    public void TC06_navigateToDecksViaEditButton() {
        UIHelper ui = new UIHelper(app.driver);

        ApplicationManager.logger.info("=== TC06: Навигация к колодам через кнопку 'Edit' ===");

        ApplicationManager.logger.info("Открываем домашнюю страницу");
        app.driver.get("https://11-silver.vercel.app/");

        ApplicationManager.logger.info("Кликаем по кнопке 'Start Training'");
        ui.click("a[href='/decks']");

        ApplicationManager.logger.info("Кликаем по первой колоде");
        WebElement firstDeck = ui.waitForVisibilityXPath("/html/body/main/div[1]/div/div[2]/div[1]/div");
        firstDeck.click();

        ApplicationManager.logger.info("Кликаем по кнопке 'Edit'");
        WebElement edit = ui.waitForVisibilityXPath("/html/body/main/div[1]/div/div[2]/div[1]/div/div[2]/button[1]");
        edit.click();

        ApplicationManager.logger.info("Ждем перехода на экран Decks");
        ui.waitForCondition(driver -> driver.getCurrentUrl().contains("/decks"));
        String currentUrl = app.driver.getCurrentUrl();
        app.softly.assertTrue(currentUrl.contains("/edit?source=/decks"), "URL должен содержать '/edit'");
        ApplicationManager.logger.info("Экран Decks успешно открыт");
    }

}