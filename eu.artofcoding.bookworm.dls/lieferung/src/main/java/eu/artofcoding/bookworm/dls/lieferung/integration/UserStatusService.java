/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.lieferung.integration;

import eu.artofcoding.bookworm.dls.lieferung.config.BlistaConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXB;
import java.io.IOException;
import java.io.StringReader;

@Service
public class UserStatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserStatusService.class);

    private final BlistaConfiguration blistaConfiguration;

    @Autowired
    UserStatusService(BlistaConfiguration blistaConfiguration) {
        this.blistaConfiguration = blistaConfiguration;
    }

    private Request makeHttpRequest(final String bookStatusUrl) {
        return new Request.Builder()
                .url(bookStatusUrl)
                .addHeader("bibliothek", blistaConfiguration.getBibliothek())
                .addHeader("bibkennwort", blistaConfiguration.getBibkennwort())
                .build();
    }

    public UserOrderStatus userStatus(final String userId) {
        final String bookStatusUrl = blistaConfiguration.getBlistaDlsUrl() + "/" + userId;
        final Request searchRequest = makeHttpRequest(bookStatusUrl);
        final OkHttpClient client = new OkHttpClient();
        try {
            okhttp3.Response execute = client.newCall(searchRequest).execute();
            if (execute.isSuccessful()) {
                final String body = execute.body().string();
                LOGGER.debug(bookStatusUrl + " -> " + body);
                final DlsBooksResponse dlsBooksResponse = JAXB.unmarshal(new StringReader(body), DlsBooksResponse.class);
                final UserOrderStatus userOrderStatus = new UserOrderStatus(userId);
                for (DlsBook book : dlsBooksResponse.getDlsBooks()) {
                    userOrderStatus.getBookOrderStatusList().add(BookOrderStatusFactory.from(book));
                }
                return userOrderStatus;
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
