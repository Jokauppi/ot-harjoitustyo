package productivetime.domain;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Properties;

public class Settings {

    private static Settings settings = null;
    private static Properties properties;
    private static final String propertiesFile = "config.properties";

    private Settings() {

        properties = new Properties();
        try {
            loadProperties();
        } catch (IOException ioException) {
            setDefaults();
            storeProperties();
        }

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

    private static void setDefaults() {
        properties.setProperty("timezone", "Europe/Helsinki");
    }

    private static void loadProperties() throws IOException {

        String propPath = propertiesFile;

        System.out.println("Load");
        System.out.println(propPath);

        FileInputStream inputStream = new FileInputStream(propPath);
        properties.load(inputStream);
    }

    private static void storeProperties() {

        String propPath = propertiesFile;

        System.out.println("Store");
        System.out.println(propPath);

        try {
            FileOutputStream outputStream = new FileOutputStream(propPath, false);
            properties.store(outputStream, "ProductiveTime configuration");
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
        return ZoneId.of(getSetting("timezone"));
    }
}
