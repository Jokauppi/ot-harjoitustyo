package productivetime.domain;

import java.io.*;
import java.util.Properties;

public class Settings {

    private static Settings singleton = null;
    private static Properties properties;
    private static final String PROP_PATH = ".ProductiveTime/config.properties";

    private Settings() {

        properties = new Properties();
        try {
            loadProperties();
        } catch (IOException ignored) {
        }
        setDefaults();
        storeProperties();

    }

    private static void initSettings() {

        if (singleton == null) {
            synchronized (Settings.class) {
                if (singleton == null) {
                    singleton = new Settings();
                }
            }
        }
    }

    private static void setDefaults() {

        Properties defaults = new Properties();

        InputStream defaultsStream = Settings.class.getClassLoader().getResourceAsStream("defaults.properties");

        try {
            defaults.load(defaultsStream);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        for (String propertyKey : defaults.stringPropertyNames()) {
            if (properties.getProperty(propertyKey) == null) {
                properties.setProperty(propertyKey, defaults.getProperty(propertyKey));
            }
        }
    }

    private static void loadProperties() throws IOException {

        FileInputStream inputStream = new FileInputStream(PROP_PATH);
        properties.load(inputStream);
    }

    private static void storeProperties() {

        try {
            File f = new File(PROP_PATH);
            f.getParentFile().mkdirs();
            f.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(f, false);
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
}
