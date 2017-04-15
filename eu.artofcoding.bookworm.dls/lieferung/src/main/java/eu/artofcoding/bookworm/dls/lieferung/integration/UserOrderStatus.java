/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.lieferung.integration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class UserOrderStatus {

    private String userId;

    private List<BookOrderStatus> bookOrderStatusList = new ArrayList<>();

    UserOrderStatus() {
    }

    UserOrderStatus(final String userId) {
        this.userId = userId;
    }

    String getUserId() {
        return userId;
    }

    void setUserId(final String userId) {
        this.userId = userId;
    }

    List<BookOrderStatus> getBookOrderStatusList() {
        return bookOrderStatusList;
    }

    void setBookOrderStatusList(final List<BookOrderStatus> bookOrderStatusList) {
        this.bookOrderStatusList = bookOrderStatusList;
    }

}
