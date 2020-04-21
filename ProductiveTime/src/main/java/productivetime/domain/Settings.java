package productivetime.domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Properties;

public class Settings {

    private static Settings settings = null;
    private static Properties properties;
    private static String propertiesFile = "config.properties";

    private Settings() {

        properties = new Properties();
        properties.setProperty("timezone", "Europe/Helsinki");

    }

    private static void initSettings() {
        if (settings == null) {
            synchronized (Settings.class) {
                if (settings == null) {
                    settings = new Settings();
                }
            }
        }
    }

    private static void loadProperties() {

        properties = new Properties();

        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String propPath = rootPath + propertiesFile;

        System.out.println(propPath);

        try {
            FileInputStream inputStream = new FileInputStream(propPath);
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            storeProperties();
            loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void storeProperties() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String propPath = rootPath + propertiesFile;

        try {
            FileOutputStream outputStream = new FileOutputStream(propPath, false);
            properties.store(outputStream, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSetting(String key) {
        initSettings();
        return properties.getProperty(key);
    }

    public static void setSetting(String key, String property) {
        initSettings();
        properties.setProperty(key, property);
        storeProperties();
    }

    public static ZoneId getTimeZone() {
        initSettings();
        return ZoneId.of(properties.getProperty("timezone"));
    }
}
