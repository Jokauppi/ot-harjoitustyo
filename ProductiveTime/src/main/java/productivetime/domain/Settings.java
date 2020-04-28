package productivetime.domain;

import java.io.*;
import java.util.Properties;

/**
 * Class offers static methods to get to get configuration variables and set new values to them.
 */
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

    /**
     * Gets the specified setting from the applications configuration files. If no configuration files are found, the
     * needed files are created and populated first with default values.
     * @param key name of setting to get.
     * @return the string value associated with the key
     */
    public static String getSetting(String key) {
        initSettings();
        return properties.getProperty(key);
    }

    /**
     * Sets a new value for the specified setting and stores it to the applications configuration files. If no configuration files are found, the
     * needed files are created and populated first with default values.
     * @param key name of setting to set.
     * @param property new value for the specified setting.
     */
    public static void setSetting(String key, String property) {
        initSettings();
        properties.setProperty(key, property);
        storeProperties();
    }
}
