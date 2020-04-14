package productivetime.domain;

import java.time.ZoneId;

public class Settings {

    private static Settings settings = null;
    private static ZoneId timeZone;

    private Settings() {
        System.out.println("timezone init");
        timeZone = ZoneId.systemDefault();
    }

    public static void initSettings() {
        if (settings == null) {
            System.out.println("settings init");
            synchronized (Settings.class) {
                if (settings == null) {
                    settings = new Settings();
                }
            }
        }
    }

    public static ZoneId getTimeZone() {
        initSettings();
        return timeZone;
    }
}