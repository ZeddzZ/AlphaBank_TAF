package org.Helpers;

public enum Settings {
    Url("appium.url"),
    PlatformName("appium.platform_name"),
    PlatformVersion("appium.platform_version"),
    DeviceName("appium.device_name"),
    AppPath("appium.app_path");

    private final String propertyName;

    Settings(String propertyName) {
        this.propertyName = propertyName;
    }

    public static Settings getByPropertyName(String propertyName) {
        Settings[] settings = Settings.values();
        for (Settings setting : settings) {
            if (setting.propertyName.equals(propertyName)) {
                return setting;
            }
        }
        return null;
    }



}
