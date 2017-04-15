/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.katalog.service;

import eu.artofcoding.bookworm.dls.katalog.integration.AvailableBooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class BookController {

    private final AvailableBooks availableBooks;

    @Autowired
    public BookController(AvailableBooks availableBooks) {
        this.availableBooks = availableBooks;
    }

    @RequestMapping(value = "/book/aghnummer/{aghNummer}/available", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BookAvailability> bookAvailable(@PathVariable("aghNummer") String aghNummer) {
        try {
            final boolean available = availableBooks.findBook(aghNummer);
            final BookAvailability bookAvailability = new BookAvailability(aghNummer, available);
            return new ResponseEntity<>(bookAvailability, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
