package org.WebDriver;

import org.openqa.selenium.WebDriver;
public class Driver {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            createDriver();
        }
        return driver;
    }

    private static void createDriver() {

    }

    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
