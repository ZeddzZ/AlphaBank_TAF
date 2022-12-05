package org.Pages;

import org.WebDriver.Driver;
import org.WebDriver.Wait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected Logger logger = LogManager.getLogger(getClass().getName());
    protected WebDriver driver = Driver.getDriver();
    protected WebDriverWait wait = Wait.getWait();

    protected static final int DEFAULT_WAIT_TIMEOUT_MILLIS = 100;

    public BasePage() {
        logger.info(String.format("Creating new instance of %s page", getClass().getName()));
        PageFactory.initElements(driver, this);
        waitForPageLoad();
    }

    protected void waitForUpdate(long millis) {
        try {
            Thread.sleep(Duration.ofMillis(millis));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected void waitForUpdate() {
        waitForUpdate(DEFAULT_WAIT_TIMEOUT_MILLIS);
    }

    protected abstract void waitForPageLoad();
}