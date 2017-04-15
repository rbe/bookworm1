/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.lieferung.service;

import eu.artofcoding.bookworm.dls.lieferung.integration.BlistaBookStatus;
import eu.artofcoding.bookworm.dls.lieferung.integration.BookOrderStatus;
import eu.artofcoding.bookworm.dls.lieferung.integration.UserOrderStatus;
import eu.artofcoding.bookworm.dls.lieferung.integration.UserStatusService;
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
public class OrderStatusController {

    private final UserStatusService userStatusService;

    private final BlistaBookStatus blistaBookStatus;

    @Autowired
    public OrderStatusController(UserStatusService userStatusService, BlistaBookStatus blistaBookStatus) {
        this.userStatusService = userStatusService;
        this.blistaBookStatus = blistaBookStatus;
    }

    @RequestMapping(value = "/user/{userId}/status", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserOrderStatus> userStatus(@PathVariable("userId") String userId) {
        final UserOrderStatus userOrderStatus = userStatusService.userStatus(userId);
        return new ResponseEntity<>(userOrderStatus, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userId}/status/{aghNummer}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BookOrderStatus> bookStatus(@PathVariable("userId") String userId,
                                                      @PathVariable("aghNummer") String aghNummer) {
        try {
            final BookOrderStatus bookOrderStatus = blistaBookStatus.bookStatus(userId, aghNummer);
            return new ResponseEntity<>(bookOrderStatus, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
