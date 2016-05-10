package eu.artofcoding.bookworm.dls.bestellung.v03.book;

import org.junit.Test;

import javax.xml.bind.JAXB;
import java.io.InputStream;

public class DlsBookResponseTest {

    @Test
    public void shouldUnmarshalBookStatusResponse() {
        final InputStream resourceAsStream = DlsBookResponseTest.class.getResourceAsStream("/dls/0.3/DlsBookStatusResponse.xml");
        final DlsBookResponse unmarshal = JAXB.unmarshal(resourceAsStream, DlsBookResponse.class);
        System.out.println(unmarshal);
    }

}
