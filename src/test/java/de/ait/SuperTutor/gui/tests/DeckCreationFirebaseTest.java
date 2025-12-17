package de.ait.SuperTutor.gui.tests;

import de.ait.SuperTutor.gui.core.TestBase;
import de.ait.SuperTutor.gui.core.UIHelper;
import de.ait.SuperTutor.utils.TokenProvider;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

public class DeckCreationFirebaseTest extends TestBase {

    @Test
    public void createDeckAfterFirebaseLogin() {

        UIHelper ui = new UIHelper(app.getDriver());

        String email = "gmtestacc14@gmail.com";
        String password = "TestTest007!";

        // Получаем реальный Firebase токен
        String idToken = TokenProvider.getFirebaseIdToken(email, password);

        // Сохраняем токен в localStorage для фронтенда
        ((JavascriptExecutor) app.getDriver()).executeScript(
                "window.localStorage.setItem('firebaseToken', arguments[0]);", idToken
        );

        // Обновляем страницу, чтобы фронтенд авторизовал пользователя
        app.getDriver().navigate().refresh();

        // Навигация и создание колоды
        ui.clickByXpath("/html/body/header/nav/div/div[1]/a[2]");
        ui.clickByXpath("/html/body/main/div[1]/div/div[3]/div/button");

        app.logger.info("Переход на decks и нажатие Create выполнены успешно!");
    }

}
