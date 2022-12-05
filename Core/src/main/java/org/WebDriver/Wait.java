package org.WebDriver;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Wait {
    private static WebDriverWait wait;
    private static int timeout = 5000;
    private static int pollingInterval = 100;

    public static WebDriverWait getWait() {
        if(wait == null) {
            createWait();
        }
        return wait;
    }
    private static void createWait() {
        wait = new WebDriverWait(Driver.getDriver(), Duration.ofMillis(timeout), Duration.ofMillis(pollingInterval));
    }
}
