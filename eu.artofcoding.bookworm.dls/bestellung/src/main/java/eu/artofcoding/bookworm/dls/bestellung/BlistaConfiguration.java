package eu.artofcoding.bookworm.dls.bestellung;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class BlistaConfiguration {

    @Value("${blista.dls.sftp.username}")
    private String sftpUsername;

    @Value("${blista.dls.sftp.password}")
    private String sftpPassword;

    @Value("${blista.dls.sftp.host}")
    private String sftpHost;

    @Value("${blista.dls.sftp.port}")
    private int sftpPort;

    String getSftpUsername() {
        return sftpUsername;
    }

    byte[] getSftpPassword() {
        return sftpPassword.getBytes();
    }

    String getSftpHost() {
        return sftpHost;
    }

    int getSftpPort() {
        return sftpPort;
    }

}
