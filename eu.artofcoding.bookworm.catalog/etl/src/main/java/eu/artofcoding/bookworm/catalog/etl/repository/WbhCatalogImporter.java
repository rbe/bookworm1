/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl.repository;

import eu.artofcoding.bookworm.catalog.etl.domain.Hoerbuch;
import eu.artofcoding.bookworm.catalog.etl.domain.Hoerbuchkatalog;
import eu.artofcoding.bookworm.catalog.etl.domain.HoerbuchkatalogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Set;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

@Service
class WbhCatalogImporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WbhCatalogImporter.class);

    private static final Path GESAMT_DAT_ARCHIVE = Paths.get("Gesamt.dat.archive");

    private final WbhCatalogConfig wbhCatalogConfig;

    private final WbhCatalogParser gesamtDatParser;

    private final HoerbuchkatalogFactory hoerbuchkatalogFactory;

    @Autowired
    public WbhCatalogImporter(WbhCatalogConfig wbhCatalogConfig,
                              WbhCatalogParser gesamtDatParser,
                              HoerbuchkatalogFactory hoerbuchkatalogFactory) {
        this.wbhCatalogConfig = wbhCatalogConfig;
        this.gesamtDatParser = gesamtDatParser;
        this.hoerbuchkatalogFactory = hoerbuchkatalogFactory;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        final Path wbhGesamtdatArchive = createDirectory(wbhCatalogConfig.getWbhGesamtdatArchive());
        process(wbhGesamtdatArchive, GESAMT_DAT_ARCHIVE);
        final Path wbhGesamtdatImport = createDirectory(wbhCatalogConfig.getWbhGesamtdatImport());
        watch(wbhGesamtdatImport);
    }

    private Path createDirectory(final Path directory) {
        if (!directory.toFile().exists()) {
            if (!directory.toFile().mkdirs()) {
                LOGGER.error("Could not create directory {}", directory.toAbsolutePath().toString());
                throw new RuntimeException();
            }
        }
        return directory;
    }

    private void archive(final Path directory, final Path fileName) {
        final Path gesamtDat = directory.resolve(fileName).toAbsolutePath();
        final Path archive = wbhCatalogConfig.getWbhGesamtdatArchive()
                .resolve(GESAMT_DAT_ARCHIVE);
        try {
            Files.move(gesamtDat, archive, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOGGER.error("Cannot archive " + fileName.toAbsolutePath(), e);
        }
    }

    private void process(final Path directory, final Path fileName) {
        LOGGER.info("Importing books");
        final Path gesamtDat = directory.resolve(fileName);
        try {
            final Set<Hoerbuch> hoerbuecher = gesamtDatParser.parse(gesamtDat);
            final Hoerbuchkatalog hoerbuchkatalog = hoerbuchkatalogFactory.erzeugen(hoerbuecher);
            LOGGER.info("Imported {} books", hoerbuchkatalog.anzahlHoerbuecher());
        } catch (IOException e) {
            LOGGER.error("Cannot import books", e);
        }
    }

    private void watch(final Path directory) {
        try {
            final WatchService watcher = FileSystems.getDefault().newWatchService();
            directory.register(watcher, ENTRY_MODIFY);
            while (true) {
                WatchKey key;
                try {
                    key = watcher.take();
                } catch (InterruptedException e) {
                    LOGGER.error("Interrupted while watching directory " + directory.toAbsolutePath(), e);
                    return;
                }
                for (WatchEvent<?> event : key.pollEvents()) {
                    @SuppressWarnings("unchecked")
                    final WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    final Path fileName = ev.context();
                    LOGGER.debug("{}: {}", event.kind().name(), fileName);
                    final boolean eventForGesamtDat = fileName.endsWith("Gesamt.dat");
                    if (eventForGesamtDat) {
                        process(directory, fileName);
                        archive(directory, fileName);
                    }
                }
                boolean keyResetted = key.reset();
                if (!keyResetted) {
                    LOGGER.error("Cannot watch directory {}", directory.toAbsolutePath());
                    break;
                }
            }
        } catch (IOException e) {
            LOGGER.error("", e);
        }
    }

}
