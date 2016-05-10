@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type=LocalDateTime.class, value=LocalDateTimeXmlAdapter.class)
})
package eu.artofcoding.bookworm.dls.bestellung.v03.book;

import eu.artofcoding.bookworm.common.helper.LocalDateTimeXmlAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDateTime;
