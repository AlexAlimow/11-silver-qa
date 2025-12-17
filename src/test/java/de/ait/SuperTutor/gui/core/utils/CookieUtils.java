package de.ait.SuperTutor.gui.core.utils;


import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.*;

public class CookieUtils {

    private static final String COOKIE_FILE = "cookies.data";

    public static void saveCookies(WebDriver driver) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COOKIE_FILE))) {
            oos.writeObject(driver.manage().getCookies());
            System.out.println("Cookies saved!");
        } catch (Exception e) {
            throw new RuntimeException("Cannot save cookies", e);
        }
    }

    public static void loadCookies(WebDriver driver) {
        File file = new File(COOKIE_FILE);
        if (!file.exists()) {
            System.out.println("No cookies file found");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Set<Cookie> cookies = (Set<Cookie>) ois.readObject();
            for (Cookie cookie : cookies) {
                driver.manage().addCookie(cookie);
            }
            System.out.println("Cookies loaded!");
        } catch (Exception e) {
            throw new RuntimeException("Cannot load cookies", e);
        }
    }
}
