/*
 * Bookworm
 *
 * Copyright (C) 2011-2016 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.bestellung.v03.order;

import com.jcraft.jsch.ChannelSftp;
import eu.artofcoding.bookworm.dls.bestellung.helper.SftpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Component
class BilletSender {

    private static final JAXBContext JAXB_CONTEXT;

    @Autowired
    private BilletFactory billetFactory;

    static {
        try {
            JAXB_CONTEXT = JAXBContext.newInstance(Billet.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private final SftpClient sftpClient;

    @Autowired
    BilletSender(final SftpClient sftpClient) {
        this.sftpClient = sftpClient;
    }

    String marshal(final Billet billet) {
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

    /**
     * Place an order at blista DLS.
     *
     * @param userId UserId.
     * @param aghNummer Bestellnummer/AGH Nummer.
     * @return Abrufkennwort.
     */
    String sendToServer(final String userId, final String aghNummer) {
        final Billet billet = billetFactory.create(userId, aghNummer);
        sendToServer(billet);
        return billet.getAbrufkennwort();
    }

    private void sendToServer(final Billet billet) {
        sftpClient.with((delegate) -> {
            delegate.cd("new");
            final String billetXml = marshal(billet);
            final String filename = String.format("%s-%s.blt", billet.getUserId(), billet.getAghNummer());
            delegate.putOverwrite(billetXml, filename);
            return null;
        });
    }

    ServerStatus serverStatus(final String userId, final String aghNummer) {
        final ServerStatus rejected = sftpClient.with((delegate) -> {
            delegate.ls("rejected", entry -> {
                final boolean hasUserId = entry.getFilename().contains(userId);
                final boolean hasBestellnumer = entry.getFilename().contains(aghNummer);
                if (hasUserId && hasBestellnumer) {
                    delegate.result = ServerStatus.REJECTED;
                    return ChannelSftp.LsEntrySelector.BREAK;
                }
                return ChannelSftp.LsEntrySelector.CONTINUE;
            });
            return null;
        });
        final ServerStatus processed = sftpClient.with((delegate) -> {
            delegate.ls("processed", entry -> {
                final boolean hasUserId = entry.getFilename().contains(userId);
                final boolean hasBestellnumer = entry.getFilename().contains(aghNummer);
                if (hasUserId && hasBestellnumer) {
                    delegate.result = ServerStatus.PROCESSED;
                    return ChannelSftp.LsEntrySelector.BREAK;
                }
                return ChannelSftp.LsEntrySelector.CONTINUE;
            });
            return null;
        });
        return null != rejected ? rejected : null != processed ? processed : null;
    }

    enum ServerStatus {
        NEW, PROCESSED, REJECTED
    }

}
