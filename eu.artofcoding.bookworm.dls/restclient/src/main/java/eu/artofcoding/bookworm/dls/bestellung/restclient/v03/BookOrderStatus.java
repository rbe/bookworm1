package eu.artofcoding.bookworm.dls.bestellung.restclient.v03;

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

    public BookOrderStatus() {
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

    public void setBestelldatum(final LocalDateTime bestelldatum) {
        this.bestelldatum = bestelldatum;
    }

    public boolean isBezugsfaehig() {
        return bezugsfaehig;
    }

    public void setBezugsfaehig(final boolean bezugsfaehig) {
        this.bezugsfaehig = bezugsfaehig;
    }

    public LocalDateTime getRueckgabedatum() {
        return rueckgabedatum;
    }

    public void setRueckgabedatum(final LocalDateTime rueckgabedatum) {
        this.rueckgabedatum = rueckgabedatum;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(final String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(final int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public int getMaxDownload() {
        return maxDownload;
    }

    public void setMaxDownload(final int maxDownload) {
        this.maxDownload = maxDownload;
    }

    public int getAusleihstatus() {
        return ausleihstatus;
    }

    public void setAusleihstatus(final int ausleihstatus) {
        this.ausleihstatus = ausleihstatus;
    }

    public String getDlsDescription() {
        return dlsDescription;
    }

    public void setDlsDescription(final String dlsDescription) {
        this.dlsDescription = dlsDescription;
    }

}
