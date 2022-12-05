package org.Tests;

import org.Helpers.Settings;
import org.Helpers.SettingsReader;
import org.WebDriver.AppiumDriver;
import org.WebDriver.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;

@Test
public abstract class BaseTest {

    protected Logger logger = LogManager.getLogger(getClass().getName());
    protected SettingsReader sr;
    @BeforeSuite
    public void BeforeAll()
    {
        sr = new SettingsReader();
        sr.MapProperties();
    }

    @BeforeMethod
    public void BeforeTest(ITestResult result) {
        DesiredCapabilities caps = AppiumDriver.createDesiredCapabilities(
                sr.getProperty(Settings.PlatformName),
                sr.getProperty(Settings.PlatformVersion),
                sr.getProperty(Settings.DeviceName),
                sr.getProperty(Settings.AppPath));
        WebDriver appiumDriver = AppiumDriver.getAppiumDriver(
                sr.getProperty(Settings.Url),
                caps);
        Driver.setDriver(appiumDriver);
        logger.info(String.format("Starting execution of test %s", result.getTestName()));
    }

    @AfterMethod
    public void AfterTest(ITestResult result) {
        logger.info(String.format("Finishing execution of test %s, result is %s", result.getName(), result.getStatus()));
        Driver.closeDriver();
    }
}
