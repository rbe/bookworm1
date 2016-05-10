package eu.artofcoding.bookworm.dls.bestellung.restclient.v03;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public final class BookOrder {

    private static final long serialVersionUID = -1L;

    private String abrufkennwort;

    private String billetStatus;

    BookOrder() {
    }

    BookOrder(final String abrufkennwort, final String billetStatus) {
        this.abrufkennwort = abrufkennwort;
        this.billetStatus = billetStatus;
    }

    @XmlElement(name = "Abrufkennwort")
    public String getAbrufkennwort() {
        return abrufkennwort;
    }

    void setAbrufkennwort(final String abrufkennwort) {
        this.abrufkennwort = abrufkennwort;
    }

    @XmlElement(name = "BilletStatus")
    public String getBilletStatus() {
        return billetStatus;
    }

    void setBilletStatus(final String billetStatus) {
        this.billetStatus = billetStatus;
    }

    @Override
    public String toString() {
        return "BookOrder{" +
                "abrufkennwort='" + abrufkennwort + '\'' +
                ", billetStatus='" + billetStatus + '\'' +
                '}';
    }

}
