/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.api.xml;

import java.util.LinkedList;
import java.util.List;

public final class XmlRow {

    private final List<XmlData> xmlDatas = new LinkedList<>();

    public void addXmlData(final XmlData xmlData) {
        xmlDatas.add(xmlData);
    }

    public List<XmlData> getXmlDatas() {
        return xmlDatas;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (XmlData xmlData : xmlDatas) {
            builder.append(xmlData.toString());
        }
        return String.format("XmlRow{xmlDatas=%s}", builder.toString());
    }

}
