package eu.artofcoding.bookworm.dls.etl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class BlistaConfiguration {

    @Value("${blista.sftp.username}")
    private String sftpUsername;

    @Value("${blista.sftp.password}")
    private String sftpPassword;

    @Value("${blista.sftp.host}")
    private String sftpHost;

    @Value("${blista.sftp.port}")
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
