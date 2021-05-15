public class ConfigFactory {
    private static Config configObject;

    public static Config getConfig() {
        return configObject;
    }

    public static void setConfig(Config config) {
        configObject = config;
    }
}
