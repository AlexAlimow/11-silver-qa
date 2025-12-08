package de.ait.taskTracker.gui.core;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    // =================== Обычные элементы ===================

    public void click(String cssSelector) {
        WebElement element = waitForClickable(cssSelector);
        safeClick(element);
    }

    public void type(String cssSelector, String text) {
        WebElement element = waitForVisibility(cssSelector);
        element.clear();
        element.sendKeys(text);
    }

    public void shouldBeVisible(String cssSelector) {
        waitForVisibility(cssSelector);
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

    public WebElement waitForClickableId(String id) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
    }

    public void waitForClickableId(String id, int seconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        customWait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
    }

    public void waitForShadowElementClickable(String[] hostTags, String elementId, String visibleText, int seconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        customWait.until(driver -> {
            WebElement el = findNestedShadowElement(hostTags, elementId, visibleText);
            return el != null && el.isDisplayed() && el.isEnabled();
        });
    }

    public void clickShadowElement(String[] hostTags, String elementId, String visibleText, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        WebElement element = wait.until(driver -> {
            try {
                return findNestedShadowElement(hostTags, elementId, visibleText);
            } catch (Exception e) {
                return null;
            }
        });

        if (element == null)
            throw new RuntimeException("Shadow element not found: id=" + elementId);

        element.click();
    }

    public boolean isElementVisible(String xpath) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForElement(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }


    public void waitForUrl(String partialUrl) {
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }

    public String getText(String cssSelector) {
        return waitForVisibility(cssSelector).getText();
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

    public void clickNestedShadowElement(String[] hostTags, String elementId, String visibleText) {
        WebElement element = findNestedShadowElement(hostTags, elementId, visibleText);
        if (element != null) {
            waitUntilClickable(element);
            safeClick(element);
        } else {
            throw new RuntimeException("Элемент с id='" + elementId + "' внутри nested shadow DOM не найден или текст не совпадает");
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

    private WebElement findNestedShadowElement(String[] hostTags, String elementId, String visibleText) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        StringBuilder script = new StringBuilder();
        script.append("let el = document.querySelector('").append(hostTags[0]).append("');\n");
        script.append("if(!el) return null;\n");
        script.append("el = el.shadowRoot;\n");
        for (int i = 1; i < hostTags.length; i++) {
            script.append("el = el.querySelector('").append(hostTags[i]).append("');\n");
            script.append("if(!el) return null;\n");
            script.append("el = el.shadowRoot;\n");
        }
        script.append("el = el.getElementById('").append(elementId).append("');\n");
        if (visibleText != null) {
            script.append("if(el && !el.textContent.includes('").append(visibleText).append("')) el=null;\n");
        }
        script.append("return el;");
        return (WebElement) js.executeScript(script.toString());
    }
}
