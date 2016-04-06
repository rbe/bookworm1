/*
 * Bookworm
 *
 * Copyright (C) 2011-2016 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.v03.bestellung;

import com.jcraft.jsch.ChannelSftp;
import eu.artofcoding.bookworm.dls.bestellung.SftpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Component
public class BilletSender {

    private static final JAXBContext JAXB_CONTEXT;

    static {
        try {
            JAXB_CONTEXT = JAXBContext.newInstance(Billet.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private final SftpClient sftpClient;

    @Autowired
    public BilletSender(final SftpClient sftpClient) {
        this.sftpClient = sftpClient;
    }

    public String marshal(final Billet billet) {
        try {
            final Marshaller marshaller = JAXB_CONTEXT.createMarshaller();
            //marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            final StringWriter writer = new StringWriter();
            marshaller.marshal(billet, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendToServer(final Billet billet) {
        sftpClient.with((delegate) -> {
            delegate.cd("new");
            final String billetXml = marshal(billet);
            final String filename = String.format("%s-%s.blt", billet.getUserId(), billet.getBestellnummer());
            delegate.putOverwrite(billetXml, filename);
            return null;
        });
    }

    public ServerStatus serverStatus(final Billet billet) {
        return sftpClient.with((delegate) -> {
            delegate.ls("rejected", entry -> {
                final boolean hasUserId = entry.getFilename().contains(billet.getUserId());
                final boolean hasBestellnumer = entry.getFilename().contains(billet.getBestellnummer());
                if (hasUserId && hasBestellnumer) {
                    delegate.result = ServerStatus.REJECTED;
                    return ChannelSftp.LsEntrySelector.BREAK;
                }
                return ChannelSftp.LsEntrySelector.CONTINUE;
            });
            return null;
        });
    }

    public enum ServerStatus {
        NEW, PROCESSED, REJECTED
    }

}
