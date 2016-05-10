package eu.artofcoding.bookworm.dls.bestellung.v03.book;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "dls")
@XmlAccessorType(XmlAccessType.PROPERTY)
class DlsBookResponse {

    private String version;

    private DlsBook dlsBook;

    @XmlElement(name = "version")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    String getVersion() {
        return version;
    }

    void setVersion(final String version) {
        this.version = version;
    }

    @XmlElement(name = "book")
    DlsBook getDlsBook() {
        return dlsBook;
    }

    void setDlsBook(final DlsBook dlsBook) {
        this.dlsBook = dlsBook;
    }

    @Override
    public String toString() {
        return "DlsBookResponse{" +
                "version='" + version + '\'' +
                ", dlsBook=" + dlsBook +
                '}';
    }

}
