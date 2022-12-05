package org.WebDriver;

import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumDriver {
    protected static Logger logger = LogManager.getLogger("AppiumDriver");
    public static WebDriver getAppiumDriver(String url,DesiredCapabilities desiredCapabilities) {

        try {
            return new io.appium.java_client.AppiumDriver(new URL(url), desiredCapabilities);
        } catch (MalformedURLException e) {
            logger.warn(String.format("Failed to start Appium driver on %s", url));
            throw new RuntimeException(e);
        }
    }

    public static DesiredCapabilities createDesiredCapabilities(String platformName, String platformVersion, String deviceName, String appPath) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        desiredCapabilities.setCapability(MobileCapabilityType.APP, appPath);
        return desiredCapabilities;
    }
}
