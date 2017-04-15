package eu.artofcoding.bookworm.dls.bestellung.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BlistaConfiguration {

    @Value("${blista.dls.sftp.username}")
    private String sftpUsername;

    @Value("${blista.dls.sftp.password}")
    private String sftpPassword;

    @Value("${blista.dls.sftp.host}")
    private String sftpHost;

    @Value("${blista.dls.sftp.port}")
    private int sftpPort;

    @Value("${blista.dls.rest.scheme}")
    private String dlsRestScheme;

    @Value("${blista.dls.rest.host}")
    private String dlsRestHost;

    @Value("${blista.dls.rest.port}")
    private int dlsRestPort;

    @Value("${blista.dls.rest.uri}")
    private String dlsRestUri;

    @Value("${blista.dls.rest.bibliothek}")
    private String bibliothek;

    @Value("${blista.dls.rest.bibkennwort}")
    private String bibkennwort;

    public String getSftpUsername() {
        return sftpUsername;
    }

    public byte[] getSftpPassword() {
        return sftpPassword.getBytes();
    }

    public String getSftpHost() {
        return sftpHost;
    }

    public int getSftpPort() {
        return sftpPort;
    }

    public String getBlistaDlsUrl() {
        return String.format("%s://%s:%s%s", dlsRestScheme, dlsRestHost, dlsRestPort, dlsRestUri);
    }

    public String getBibliothek() {
        return bibliothek;
    }

    public String getBibkennwort() {
        return bibkennwort;
    }

}
