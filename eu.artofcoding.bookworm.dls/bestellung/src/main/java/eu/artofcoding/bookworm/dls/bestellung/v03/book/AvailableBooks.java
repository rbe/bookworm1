/*
 * Bookworm
 *
 * Copyright (C) 2011-2016 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.bestellung.v03.book;

import eu.artofcoding.bookworm.dls.bestellung.config.BlistaConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

@Service
class AvailableBooks {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvailableBooks.class);

    private final BlistaConfiguration blistaConfiguration;

    private final ReentrantLock lock = new ReentrantLock();

    private CopyOnWriteArrayList<String> strings = new CopyOnWriteArrayList<>();

    @Autowired
    public AvailableBooks(BlistaConfiguration blistaConfiguration) {
        this.blistaConfiguration = blistaConfiguration;
    }

    @PostConstruct
    private void postConstruct() {
        fetchBooks();
    }

    boolean findBook(final String aghNummer) {
        //return Collections.binarySearch(strings, aghNummer);
        //strings.parallelStream().filter(s -> s.equals("01-0000122-3-9")).count()
       return strings.parallelStream().anyMatch(aghNummer::equals);
    }

    @Scheduled(cron = "0 0 * * * *")
    private void fetchBooks() {
        try {
            lock.tryLock();
            LOGGER.info("Refreshing available books from " + blistaConfiguration.getBlistaDlsCatalogUrl());
            boolean success = false;
            while (!success) {
                try {
                    final List<String> contentFromZip = getFileContentFromZip("/iso.txt");
                    if (null != contentFromZip && contentFromZip.size() > 1000) {
                        strings.addAllAbsent(contentFromZip);
                        strings.retainAll(contentFromZip);
                        success = true;
                        LOGGER.info("Available books refreshed sucessfully");
                    } else {
                        LOGGER.warn("Got less than 1000 records from service");
                    }
                } catch (IOException e) {
                    LOGGER.error("Exception while refreshing available books", e);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private List<String> getFileContentFromZip(final String filePath) throws IOException {
        final Path download = downloadUsingNIO(blistaConfiguration.getBlistaDlsCatalogUrl());
        try (FileSystem zipFileSystem = FileSystems.newFileSystem(download, null)) {
            final Path path = zipFileSystem.getPath(filePath);
            final List<String> strings = Files.readAllLines(path);
            Collections.sort(strings);
            return strings;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            download.toFile().delete();
        }
    }

    private Path downloadUsingNIO(final String urlStr) throws IOException {
        final Path tempFile = Files.createTempFile("blista", ".zip");
        final URL url = new URL(urlStr);
        final ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        final ByteBuffer byteBuffer = ByteBuffer.allocate(1440);
        final FileChannel fileChannel = (FileChannel) Files.newByteChannel(tempFile, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.APPEND));
        while (rbc.read(byteBuffer) > -1) {
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        rbc.close();
        fileChannel.close();
        return tempFile;
    }

}
