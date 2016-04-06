package eu.artofcoding.bookworm.dls.v03.bestellung;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <book>
 * <DlsID> 294 </DlsID>
 * <Bestellnummer> 1-0000122-3-9 </Bestellnummer>
 * <Kundennummer> titusTest </Kundennummer>
 * <Ausleihstatus> 3 </Ausleihstatus>
 * <Streampath> </Streampath>
 * <Bestelldatum> 2016-04-05 09:24:56 </Bestelldatum>
 * <Rueckgabedatum> 2016-05-05 00:00:00 </Rueckgabedatum>
 * <Title> Die Löwin Elsa und ihre Jungen </Title>
 * <DlsDescription> Bereitstellung abgeschlossen. </DlsDescription>
 * <Progress> 8 </Progress>
 * <MaxProgress> 8 </MaxProgress>
 * <DownloadCount> 0 </DownloadCount>
 * <MaxDownload> 5 </MaxDownload>
 * <Gesperrt> 0 </Gesperrt>
 * <DownloadLink> http://dls-test-katalog.blista.de/download/wbh06/titusTest/1-0000122-3-9/cof67n4m68fhitcht9onr5lpcg </DownloadLink>
 * <DownloadServer> dls-test-katalog.blista.de </DownloadServer>
 * <Typ> BOOK </Typ>
 * </book>
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DlsBook {

    private String dlsId;

    private String bestellnummer; // AGH-Nummer

    private String kundennummer; // UserID

    private int ausleihstatus;

    private String streampath;

    private String bestelldatum;

    private String rueckgabedatum;

    private String title;

    private String dlsDescription;

    private int progress;

    private int maxProgress;

    private int downloadCount;

    private int maxDownload;

    private int gesperrt;

    private String downloadLink;

    private String typ;

    @XmlElement(name = "DlsID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public String getDlsId() {
        return dlsId;
    }

    public void setDlsId(final String dlsId) {
        this.dlsId = dlsId;
    }

    @XmlElement(name = "Bestellnummer")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public String getBestellnummer() {
        return bestellnummer;
    }

    public void setBestellnummer(final String bestellnummer) {
        this.bestellnummer = bestellnummer;
    }

    @XmlElement(name = "Kundennummer")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public String getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(final String kundennummer) {
        this.kundennummer = kundennummer;
    }

    @XmlElement(name = "Ausleihstatus")
    public int getAusleihstatus() {
        return ausleihstatus;
    }

    public void setAusleihstatus(final int ausleihstatus) {
        this.ausleihstatus = ausleihstatus;
    }

    @XmlElement(name = "Streampath")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public String getStreampath() {
        return streampath;
    }

    public void setStreampath(final String streampath) {
        this.streampath = streampath;
    }

    @XmlElement(name = "Bestelldatum")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public String getBestelldatum() {
        return bestelldatum;
    }

    public void setBestelldatum(final String bestelldatum) {
        this.bestelldatum = bestelldatum;
    }

    @XmlElement(name = "Rueckgabedatum")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public String getRueckgabedatum() {
        return rueckgabedatum;
    }

    public void setRueckgabedatum(final String rueckgabedatum) {
        this.rueckgabedatum = rueckgabedatum;
    }

    @XmlElement(name = "Title")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @XmlElement(name = "DlsDescription")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public String getDlsDescription() {
        return dlsDescription;
    }

    public void setDlsDescription(final String dlsDescription) {
        this.dlsDescription = dlsDescription;
    }

    @XmlElement(name = "Progress")
    public int getProgress() {
        return progress;
    }

    public void setProgress(final int progress) {
        this.progress = progress;
    }

    @XmlElement(name = "MaxProgress")
    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(final int maxProgress) {
        this.maxProgress = maxProgress;
    }

    @XmlElement(name = "DownloadCount")
    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(final int downloadCount) {
        this.downloadCount = downloadCount;
    }

    @XmlElement(name = "MaxDownload")
    public int getMaxDownload() {
        return maxDownload;
    }

    public void setMaxDownload(final int maxDownload) {
        this.maxDownload = maxDownload;
    }

    @XmlElement(name = "Gesperrt")
    public int getGesperrt() {
        return gesperrt;
    }

    public void setGesperrt(final int gesperrt) {
        this.gesperrt = gesperrt;
    }

    @XmlElement(name = "DownloadLink")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(final String downloadLink) {
        this.downloadLink = downloadLink;
    }

    @XmlElement(name = "Typ")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public String getTyp() {
        return typ;
    }

    public void setTyp(final String typ) {
        this.typ = typ;
    }

    @Override
    public String toString() {
        return "DlsBook{" +
                "dlsId='" + dlsId + '\'' +
                ", bestellnummer='" + bestellnummer + '\'' +
                ", kundennummer='" + kundennummer + '\'' +
                ", ausleihstatus=" + ausleihstatus +
                ", streampath='" + streampath + '\'' +
                ", bestelldatum=" + bestelldatum +
                ", rueckgabedatum=" + rueckgabedatum +
                ", title='" + title + '\'' +
                ", dlsDescription='" + dlsDescription + '\'' +
                ", progress=" + progress +
                ", maxProgress=" + maxProgress +
                ", downloadCount=" + downloadCount +
                ", maxDownload=" + maxDownload +
                ", gesperrt=" + gesperrt +
                ", downloadLink='" + downloadLink + '\'' +
                ", typ='" + typ + '\'' +
                '}';
    }

}
