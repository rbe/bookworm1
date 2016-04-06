package eu.artofcoding.bookworm.dls.v03.bestellung;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "dls")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DlsBookResponse {

    private String version;

    private DlsBook dlsBook;

    @XmlElement(name = "version")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    @XmlElement(name = "book")
    public DlsBook getDlsBook() {
        return dlsBook;
    }

    public void setDlsBook(final DlsBook dlsBook) {
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
