/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.api.xml;

public final class XmlData {

    private String tagName;

    private String tagContent;

    public XmlData() {
    }

    public XmlData(String tagName, String tagContent) {
        this.tagName = tagName;
        this.tagContent = tagContent;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagContent() {
        return tagContent;

    }

    public void setTagContent(String tagContent) {
        this.tagContent = tagContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XmlData)) return false;
        XmlData xmlData = (XmlData) o;
        if (!tagContent.equals(xmlData.tagContent)) return false;
        if (!tagName.equals(xmlData.tagName)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = tagName.hashCode();
        result = 31 * result + tagContent.hashCode();
        return result;
    }

}
