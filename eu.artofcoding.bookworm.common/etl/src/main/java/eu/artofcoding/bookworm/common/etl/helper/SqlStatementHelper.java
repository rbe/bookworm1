/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.etl.helper;

import eu.artofcoding.bookworm.common.etl.SqlStatementCapable;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public final class SqlStatementHelper {

    private SqlStatementHelper() {
        throw new AssertionError();
    }

    public static void writeInsertStatement(final OutputStream outputStream, final SqlStatementCapable sql) {
        try {
            outputStream.write(sql.toInsertStatement().getBytes());
            outputStream.write("\n".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static OutputStream makeSqlOutputStream(final Path sqlFile)  {
        try {
            return Files.newOutputStream(sqlFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static OutputStream makeSqlOutputStream(final String directory, final String sqlFile) {
        final URL resource = XmlStreamHelper.class.getResource(directory);
        try {
            final Path outputDirectory = Paths.get(resource.toURI());
            final Path sqlFilePath = Paths.get(outputDirectory.toString(), sqlFile);
            return makeSqlOutputStream(sqlFilePath);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
