package eu.artofcoding.bookworm.dls.bestellung.v03.user;

import eu.artofcoding.bookworm.dls.bestellung.v03.book.DlsBook;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlRootElement(name = "dls")
@XmlAccessorType(XmlAccessType.PROPERTY)
class DlsBooksResponse {

    private String version;

    private List<DlsBook> dlsBooks;

    @XmlElement(name = "version")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    String getVersion() {
        return version;
    }

    void setVersion(final String version) {
        this.version = version;
    }

    @XmlElementWrapper(name="books")
    @XmlElement(name = "book")
    List<DlsBook> getDlsBooks() {
        return dlsBooks;
    }

    void setDlsBooks(final List<DlsBook> dlsBooks) {
        this.dlsBooks = dlsBooks;
    }

    @Override
    public String toString() {
        return "DlsBooksResponse{" +
                "version='" + version + '\'' +
                ", dlsBooks=" + dlsBooks +
                '}';
    }

}
