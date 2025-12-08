package de.ait.taskTracker.gui.core;

import de.ait.taskTracker.config.AppConfigApi;
import de.ait.taskTracker.utils.MyListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class ApplicationManager {

    public static Logger logger = LoggerFactory.getLogger(AppConfigApi.class);
    public static SoftAssert softly = new SoftAssert();

    public WebDriver driver;
    String browser;

    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    public WebDriver startTest() {

        switch (browser) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();

                // 🔥 Основные флаги, отключающие окно "Continue as Test"
                options.addArguments("--disable-features=ChromeBrowserCloudManagement");
                options.addArguments("--disable-features=AccountConsistency");
                options.addArguments("--disable-features=DeviceAccountConsistency");
                options.addArguments("--disable-sync");

                // 🔧 Убираем приветственные экраны Chrome
                options.addArguments("--no-first-run");
                options.addArguments("--no-default-browser-check");

                // Можно включить инкогнито, чтобы не грузились аккаунты Chrome
                options.addArguments("--incognito");

                driver = new ChromeDriver(options);
            }

            case "firefox" -> driver = new FirefoxDriver();
            case "edge" -> driver = new EdgeDriver();
        }

        WebDriverListener listener = new MyListener();
        driver = new EventFiringDecorator<>(listener).decorate(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        return driver;
    }

    public void stopTest() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
