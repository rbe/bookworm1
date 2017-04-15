/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl.repository;

import eu.artofcoding.bookworm.catalog.etl.domain.Hoerbuch;
import eu.artofcoding.bookworm.catalog.etl.domain.HoerbuchFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
class WbhCatalogParser {

    private final WbhCatalogConfig wbhCatalogConfig;

    private final HoerbuchFactory hoerbuchFactory;

    @Autowired
    public WbhCatalogParser(WbhCatalogConfig wbhCatalogConfig, HoerbuchFactory hoerbuchFactory) {
        this.wbhCatalogConfig = wbhCatalogConfig;
        this.hoerbuchFactory = hoerbuchFactory;
    }

    List<Hoerbuch> parse(final String body) throws IOException {
        // A line, terminated by CRLF
        String[] lines = body.split("\r\n");
        final List<Hoerbuch> hoerbuchEntities = new ArrayList<>();
        for (String line : lines) {
            final boolean lineIsEmpty = line.trim().isEmpty();
            if (!lineIsEmpty) {
                final Hoerbuch hoerbuch = hoerbuchFactory.fromGesamtDat(line);
                hoerbuchEntities.add(hoerbuch);
            }
        }
        return hoerbuchEntities;
    }

    Set<Hoerbuch> parse(final Path gesamtDat) throws IOException {
        final Charset charset = Charset.forName(wbhCatalogConfig.getWbhGesamtdatCharset());
        final BufferedReader reader = Files.newBufferedReader(gesamtDat, charset);
        final Set<Hoerbuch> hoerbuecher = new HashSet<>();
        String line;
        while (null != (line = reader.readLine()) && line.trim().length() > 1) {
            final Hoerbuch hoerbuch = hoerbuchFactory.fromGesamtDat(line);
            hoerbuecher.add(hoerbuch);
        }
        return hoerbuecher;
    }

}
