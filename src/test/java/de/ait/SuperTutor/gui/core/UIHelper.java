package de.ait.SuperTutor.gui.core;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public class UIHelper {

    private WebDriver driver;
    private WebDriverWait wait;

    public UIHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // =================== Обычные элементы ===================

    public void click(String cssSelector) {
        WebElement element = waitForClickable(cssSelector);
        safeClick(element);
    }

    public void clickByXpath(String xpath) {
        WebElement element = waitForClickable(xpath);
        element.click();
    }


    public void type(String cssSelector, String text) {
        WebElement element = waitForVisibility(cssSelector);
        element.clear();
        element.sendKeys(text);
    }


    public void waitAndClick(String cssSelector) {
        WebElement element = waitForVisibility(cssSelector);
        safeClick(element);
    }

    public boolean waitForTextNotEquals(String cssSelector, String oldText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(driver -> {
            try {
                String newText = driver.findElement(By.cssSelector(cssSelector)).getText();
                return !newText.equals(oldText);
            } catch (Exception e) {
                return false;
            }
        });
    }

    public void waitAndClick(String cssSelector) {
        WebElement element = waitForVisibility(cssSelector);
        safeClick(element);
    }

    public boolean waitForTextNotEquals(String cssSelector, String oldText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(driver -> {
            try {
                String newText = driver.findElement(By.cssSelector(cssSelector)).getText();
                return !newText.equals(oldText);
            } catch (Exception e) {
                return false;
            }
        });
    }

    public WebElement waitForVisibility(String cssSelector) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
    }

    public WebElement waitForVisibilityXPath(String xpath) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public WebElement waitForClickable(String cssSelector) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
    }


    public void waitForUrl(String partialUrl) {
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }


    public void clickById(String id) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
        safeClick(element);
    }

    // =================== Клик по тексту ===================

    public void clickButtonByText(String text) {
        try {
            WebElement spanElement = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[text()='" + text + "']")));
            WebElement parentButton = spanElement.findElement(By.xpath("./ancestor::button"));
            if (parentButton != null && parentButton.isDisplayed() && parentButton.isEnabled()) {
                safeClick(parentButton);
                return;
            }
            safeClick(spanElement);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось кликнуть по кнопке/спану с текстом: " + text, e);
        }
    }

    // =================== Shadow DOM ===================

    public void clickShadowElementById(String hostTag, String elementId, String visibleText) {
        WebElement element = findShadowElement(hostTag, elementId, visibleText);
        if (element != null) {
            waitUntilClickable(element);
            safeClick(element);
        } else {
            throw new RuntimeException(
                    "Элемент с id='" + elementId + "' внутри shadow root '" + hostTag + "' не найден или текст не совпадает");
        }
    }


    // =================== Универсальный метод клика ===================

    public void clickUniversal(String hostTag, String shadowId, String shadowText,
                               String cssSelector, String id, String text) {
        try {
            if (hostTag != null && shadowId != null) {
                clickShadowElementById(hostTag, shadowId, shadowText);
                return;
            }
        } catch (Exception ignored) {
        }

        try {
            if (text != null) {
                clickButtonByText(text);
                return;
            }
        } catch (Exception ignored) {
        }

        try {
            if (cssSelector != null) {
                click(cssSelector);
                return;
            }
        } catch (Exception ignored) {
        }

        try {
            if (id != null) {
                clickById(id);
                return;
            }
        } catch (Exception ignored) {
        }

        throw new RuntimeException("Не удалось кликнуть по элементу. Проверь локаторы/текст.");
    }

    // =================== Вспомогательные методы ===================

    private void safeClick(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", element);
        }
    }

    private void waitUntilClickable(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> element.isDisplayed() && element.isEnabled());
    }

    private WebElement findShadowElement(String hostTag, String elementId, String visibleText) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = "const host = document.querySelector('" + hostTag + "');" +
                "if(!host) return null;" +
                "const shadow = host.shadowRoot;" +
                "let el = shadow.getElementById('" + elementId + "');" +
                (visibleText != null ? "if(el && !el.textContent.includes('" + visibleText + "')) el=null;" : "") +
                "return el;";
        return (WebElement) js.executeScript(script);
    }

    public void waitForCondition(Function<WebDriver, Boolean> condition) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(condition);
    }


}
