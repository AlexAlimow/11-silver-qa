package de.ait.SuperTutor.gui.tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CardNavigationTest {

    @Test
    void openPageAndClickNextAndPrev() throws InterruptedException {
        // Настройка драйвера
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // Открываем страницу
        driver.get("https://11-silver.vercel.app/decks/1");

        // Ждём 2 секунды, чтобы карточка загрузилась
        Thread.sleep(2000);

        // Находим кнопку "Следующая" и кликаем
        WebElement nextButton = driver.findElement(By.cssSelector("button:has(svg.lucide-arrow-right)"));
        nextButton.click();

        // Ждём 1 секунду
        Thread.sleep(1000);

        // Находим кнопку "Предыдущая" и кликаем
        WebElement prevButton = driver.findElement(By.cssSelector("button:has(svg.lucide-arrow-left)"));
        prevButton.click();

        // Ждём 1 секунду
        Thread.sleep(1000);

        // Закрываем браузер
        driver.quit();
    }
}