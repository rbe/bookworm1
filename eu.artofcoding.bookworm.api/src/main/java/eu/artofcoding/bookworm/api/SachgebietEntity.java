/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.api;

import java.io.Serializable;

public class SachgebietEntity implements Serializable {

    private String name;

    private String description;

    public SachgebietEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (null != name && name.length() > 0) {
            builder.append(name);
            builder.append(" - ");
        }
        if (null != description && description.length() > 0) {
            builder.append(description);
        }
        return builder.toString();
    }

}
