package de.ait.SuperTutor.gui.tests;

import de.ait.SuperTutor.gui.core.ApplicationManager;
import de.ait.SuperTutor.gui.core.BaseLoginTest;
import de.ait.SuperTutor.gui.core.UIHelper;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CardNavigationTest extends BaseLoginTest {

    private UIHelper ui;

    @Test(groups = {"regression"})
    public void TC05_checkCardNavigation() throws InterruptedException {

        ui = new UIHelper(app.getDriver());
        SoftAssert soft = new SoftAssert();

        // 1. Открываем страницу
        ApplicationManager.logger.info("Step 1: Открываем страницу карточек");
        app.getDriver().get("https://11-silver.vercel.app/decks/1");
        Thread.sleep(1500);

        // ---- Считываем номер карточки ДО клика ----
        String beforeNext = ui.waitForVisibility("p.text-xs.text-darkest\\/60").getText();
        ApplicationManager.logger.info("Текущий номер карточки: " + beforeNext);

        // 2. Кликаем 'Next'
        ApplicationManager.logger.info("Step 2: Кликаем кнопку 'Next'");
        ui.waitAndClick("button:has(svg.lucide-arrow-right)");

        // ---- Ждем изменения счетчика ----
        ApplicationManager.logger.info("→ Проверяем, что счетчик карточек изменился после Next");
        soft.assertTrue(
                ui.waitForTextNotEquals("p.text-xs.text-darkest\\/60", beforeNext),
                "Счетчик карточек НЕ изменился после Next!"
        );

        String afterNext = ui.waitForVisibility("p.text-xs.text-darkest\\/60").getText();
        ApplicationManager.logger.info("Теперь номер карточки: " + afterNext);

        // 3. Кликаем 'Prev'
        ApplicationManager.logger.info("Step 3: Кликаем кнопку 'Prev'");
        ui.waitAndClick("button:has(svg.lucide-arrow-left)");

        // ---- Ждем изменения обратно ----
        ApplicationManager.logger.info("→ Проверяем, что счетчик карточек изменился после Prev");
        soft.assertTrue(
                ui.waitForTextNotEquals("p.text-xs.text-darkest\\/60", afterNext),
                "Счетчик карточек НЕ изменился после Prev!"
        );

        String afterPrev = ui.waitForVisibility("p.text-xs.text-darkest\\/60").getText();
        ApplicationManager.logger.info("Теперь номер карточки снова: " + afterPrev);

        ApplicationManager.logger.info("TC01_checkCardNavigation завершён");

        soft.assertAll();
    }
}