/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
class WbhCatalogConfig {

    @Value("${catalog.wbh.gesamtdat.import}")
    private String wbhGesamtdatImport;

    @Value("${catalog.wbh.gesamtdat.charset}")
    private String wbhGesamtdatCharset;

    @Value("${catalog.wbh.gesamtdat.archive}")
    private String wbhGesamtdatArchive;

    Path getWbhGesamtdatImport() {
        return Paths.get(wbhGesamtdatImport).toAbsolutePath();
    }

    void setWbhGesamtdatImport(final String wbhGesamtdatImport) {
        this.wbhGesamtdatImport = wbhGesamtdatImport;
    }

    String getWbhGesamtdatCharset() {
        return wbhGesamtdatCharset;
    }

    void setWbhGesamtdatCharset(final String wbhGesamtdatCharset) {
        this.wbhGesamtdatCharset = wbhGesamtdatCharset;
    }

    Path getWbhGesamtdatArchive() {
        return Paths.get(wbhGesamtdatArchive).toAbsolutePath();
    }

    void setWbhGesamtdatArchive(final String wbhGesamtdatArchive) {
        this.wbhGesamtdatArchive = wbhGesamtdatArchive;
    }

}
