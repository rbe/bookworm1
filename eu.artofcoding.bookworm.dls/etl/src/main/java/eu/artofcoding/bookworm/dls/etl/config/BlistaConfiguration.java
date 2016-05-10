package eu.artofcoding.bookworm.dls.etl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BlistaConfiguration {

    @Value("${blista.dls.rest.scheme}")
    private String restScheme;

    @Value("${blista.dls.rest.host}")
    private String restHost;

    @Value("${blista.dls.rest.port}")
    private int restPort;

    @Value("${blista.dls.rest.uri}")
    private String restUri;

    public String getBlistaUrl() {
        return String.format("%s://%s:%s%s", restScheme, restHost, restPort, restUri);
    }

}
