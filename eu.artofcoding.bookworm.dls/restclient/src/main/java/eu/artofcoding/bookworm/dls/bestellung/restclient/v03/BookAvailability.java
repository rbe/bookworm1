package eu.artofcoding.bookworm.dls.bestellung.restclient.v03;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookAvailability {

    private String aghNummer;

    private boolean available;

    public BookAvailability(final String aghNummer, final boolean available) {
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
