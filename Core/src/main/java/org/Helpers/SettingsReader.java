package org.Helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SettingsReader {
    protected Logger logger = LogManager.getLogger(getClass().getName());
    private Path settingsFilePath;
    private Map<String, String> allProperties;
    private Map<Settings, String> settingsProperties;

    public SettingsReader() {
        this(Path.of(
                System.getProperty("user.dir"),
                "src\\main\\resources",
                "config.properties"));
    }
    public SettingsReader(String path) {
        this(Path.of(path));
    }
    public SettingsReader(Path path) {
        settingsFilePath = path;
        allProperties = new HashMap<>();
        settingsProperties = new HashMap<>();
    }

    public Properties readProperties(Path path) {

        logger.info(String.format("Trying to read properties from file %s", path));
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(path.toString())) {

            properties.load(inputStream);
        }
        catch (FileNotFoundException e) {
            logger.warn(String.format("File with path %s was not found!", path));
        } catch (IOException e) {
            logger.warn(String.format("Failed to load properties from file %s", path));
        }
        return properties;
    }

    public void MapProperties() {
        Properties properties = readProperties(settingsFilePath);
        for (String key: properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            logger.info(String.format("Found property with name '%s' and value '%s'", key, value));
            allProperties.put(key, value);
            Settings possibleSetting = Settings.getByPropertyName(key);
            if(possibleSetting != null) {
                settingsProperties.put(possibleSetting, value);
            }
        }
    }

    public Map<String, String> getAllProperties() {
        return allProperties;
    }

    public Map<Settings, String> getSettingsProperties() {
        return settingsProperties;
    }

    public String getProperty(Settings setting) {
        return settingsProperties.get(setting);
    }

    public String getProperty(String prop) {
        return allProperties.get(prop);
    }
}
