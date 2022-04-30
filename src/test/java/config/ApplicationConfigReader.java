package config;

import ru.qatools.properties.Property;
import ru.qatools.properties.PropertyLoader;
import ru.qatools.properties.Resource;

@Resource.Classpath({"ApplicationConfig.properties"})

public class ApplicationConfigReader {

    @Property("BaseUrl")
    private String BaseUrl;

    @Property("version")
    private String version;


    public ApplicationConfigReader() {
        PropertyLoader.newInstance().populate(this);
    }

    public String getBaseUrl() {return this.BaseUrl;}

    public String getVersion() {return this.version;}
}
