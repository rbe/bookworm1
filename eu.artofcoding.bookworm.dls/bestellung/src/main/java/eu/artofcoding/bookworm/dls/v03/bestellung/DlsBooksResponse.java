package eu.artofcoding.bookworm.dls.v03.bestellung;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlRootElement(name = "dls")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DlsBooksResponse {

    private String version;

    private List<DlsBook> dlsBooks;

    @XmlElement(name = "version")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    @XmlElement(name = "books")
    public List<DlsBook> getDlsBooks() {
        return dlsBooks;
    }

    public void setDlsBooks(final List<DlsBook> dlsBooks) {
        this.dlsBooks = dlsBooks;
    }

    @Override
    public String toString() {
        return "DlsBookResponse{" +
                "version='" + version + '\'' +
                ", dlsBooks=" + dlsBooks +
                '}';
    }

}
