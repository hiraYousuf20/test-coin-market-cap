package config;

public class ConfigProperties {
    public static ApplicationConfigReader appConfig = new ApplicationConfigReader();

    public static String BaseUrl = appConfig.getBaseUrl();
    public static String version = appConfig.getVersion();
}
