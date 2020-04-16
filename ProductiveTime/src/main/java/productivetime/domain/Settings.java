package productivetime.domain;

import java.time.ZoneId;

public class Settings {

    private static Settings settings = null;
    private static ZoneId timeZone;

    private Settings() {
        timeZone = ZoneId.of("Europe/Helsinki");
    }

    public static void initSettings() {
        if (settings == null) {
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
