package eu.artofcoding.bookworm.dls.bestellung.v03.book;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
class BookAvailability {

    private String aghNummer;

    private boolean available;

    BookAvailability(final String aghNummer, final boolean available) {
        this.aghNummer = aghNummer;
        this.available = available;
    }

    public BookAvailability() {
    }

    public String getAghNummer() {
        return aghNummer;
    }

    public void setAghNummer(final String aghNummer) {
        this.aghNummer = aghNummer;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(final boolean available) {
        this.available = available;
    }

}
