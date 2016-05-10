/*
 * Bookworm
 *
 * Copyright (C) 2011-2016 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.bestellung.helper;

import eu.artofcoding.bookworm.dls.bestellung.config.AppConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Ignore
public class SftpClientTest {

    @Autowired
    private SftpClient sftpClient;

    @Test
    public void shouldLogin() {
        sftpClient.with((delegate) -> {
            assertEquals("/", delegate.pwd());
            return null;
        });
    }

    @Test
    public void shouldPutFileIntoNew() {
        sftpClient.with((delegate) -> {
            delegate.cd("new");
            assertEquals("/new", delegate.pwd());
            final InputStream resourceAsStream = SftpClientTest.class.getResourceAsStream("/dls/0.3/ExampleBillet.xml");
            delegate.putOverwrite(resourceAsStream, "titusTest-1-0000122-3-9.blt");
            return null;
        });
    }

}
