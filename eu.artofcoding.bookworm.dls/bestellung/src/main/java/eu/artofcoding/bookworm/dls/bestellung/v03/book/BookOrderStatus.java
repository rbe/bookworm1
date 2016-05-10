package eu.artofcoding.bookworm.dls.bestellung.v03.book;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class BookOrderStatus {

    private String aghNummer;

    private LocalDateTime bestelldatum;

    private boolean bezugsfaehig;

    private LocalDateTime rueckgabedatum;

    private String downloadLink;

    private int downloadCount;

    private int maxDownload;

    private int ausleihstatus;

    private String dlsDescription;

    BookOrderStatus() {
    }

    public String getAghNummer() {
        return aghNummer;
    }

    public void setAghNummer(final String aghNummer) {
        this.aghNummer = aghNummer;
    }

    public LocalDateTime getBestelldatum() {
        return bestelldatum;
    }

    void setBestelldatum(final LocalDateTime bestelldatum) {
        this.bestelldatum = bestelldatum;
    }

    public boolean isBezugsfaehig() {
        return bezugsfaehig;
    }

    void setBezugsfaehig(final boolean bezugsfaehig) {
        this.bezugsfaehig = bezugsfaehig;
    }

    public LocalDateTime getRueckgabedatum() {
        return rueckgabedatum;
    }

    void setRueckgabedatum(final LocalDateTime rueckgabedatum) {
        this.rueckgabedatum = rueckgabedatum;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    void setDownloadLink(final String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    void setDownloadCount(final int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public int getMaxDownload() {
        return maxDownload;
    }

    void setMaxDownload(final int maxDownload) {
        this.maxDownload = maxDownload;
    }

    public int getAusleihstatus() {
        return ausleihstatus;
    }

    void setAusleihstatus(final int ausleihstatus) {
        this.ausleihstatus = ausleihstatus;
    }

    public String getDlsDescription() {
        return dlsDescription;
    }

    void setDlsDescription(final String dlsDescription) {
        this.dlsDescription = dlsDescription;
    }

}
