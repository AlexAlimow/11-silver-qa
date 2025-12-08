package de.ait.taskTracker.gui.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UIHelper {

    private WebDriver driver;
    private WebDriverWait wait;

    public UIHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Клик по элементу по CSS
    public void click(String cssSelector) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
        element.click();
    }

    // Ввод текста
    public void type(String cssSelector, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
        element.clear();
        element.sendKeys(text);
    }

    // Проверка видимости элемента
    public void shouldBeVisible(String cssSelector) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
    }

    // Ожидание URL
    public void waitForUrl(String partialUrl) {
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }

    // Получить текст элемента
    public String getText(String cssSelector) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
        return element.getText();
    }

    // Универсальный метод клика по кнопке или span с текстом
    public void clickButtonByText(String text) {
        try {
            // Ищем span с текстом
            WebElement spanElement = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[text()='" + text + "']")));

            // Пытаемся найти родительский button
            WebElement parentButton = spanElement.findElement(By.xpath("./ancestor::button"));
            if (parentButton != null) {
                parentButton.click();
                return;
            }

            // Если родительский button не найден, кликаем по span
            spanElement.click();

        } catch (Exception e) {
            throw new RuntimeException("Не удалось кликнуть по кнопке/спану с текстом: " + text, e);
        }
    }
}
