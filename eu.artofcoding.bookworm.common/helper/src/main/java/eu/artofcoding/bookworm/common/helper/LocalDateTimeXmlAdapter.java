package eu.artofcoding.bookworm.common.helper;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeXmlAdapter extends XmlAdapter<String, LocalDateTime> {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String marshal(LocalDateTime dateTime) throws Exception {
        return dateTime.toString();
    }

    @Override
    public LocalDateTime unmarshal(String dateTime) throws Exception {
        return LocalDateTime.parse(dateTime);
    }

}
