package de.ait.SuperTutor.gui.tests;

import de.ait.SuperTutor.gui.core.ApplicationManager;
import de.ait.SuperTutor.gui.core.BaseLoginTest;
import de.ait.SuperTutor.gui.core.UIHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DeleteDeckTest extends BaseLoginTest {

    private UIHelper ui;

    @Test(groups = {"regression"})
    public void TC09_deleteDeck() throws InterruptedException {
        ui = new UIHelper(app.getDriver());
        SoftAssert soft = new SoftAssert();

        String deckName = "first";

        ApplicationManager.logger.info("Step 1: Открываем страницу колод");
        app.getDriver().get("https://11-silver.vercel.app/decks");
        Thread.sleep(1500); // чтобы страница прогрузилась

        ApplicationManager.logger.info("Step 2: Нажимаем кнопку Delete на колоде");
        WebElement deleteBtn = ui.waitForVisibilityXPath("//button[@aria-label='Delete " + deckName + "']");
        Thread.sleep(500); // чтобы анимация сработала
        deleteBtn.click();

        ApplicationManager.logger.info("Step 3: Подтверждаем удаление (Yes)");
        WebElement yesBtn = ui.waitForVisibilityXPath("//button[text()='Yes']");
        Thread.sleep(500); // ждем анимацию модалки
        yesBtn.click();

        ApplicationManager.logger.info("Step 4: Проверяем, что колоды больше нет");
        Thread.sleep(1000); // даем время на удаление из DOM
        boolean deckDeleted = app.getDriver()
                .findElements(By.xpath("//h2[text()='" + deckName + "']")).isEmpty();
        soft.assertTrue(deckDeleted, "Колода НЕ была удалена!");

        ApplicationManager.logger.info("TC09_deleteDeck завершён");

        soft.assertAll();
    }
}
