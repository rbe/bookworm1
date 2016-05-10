package eu.artofcoding.bookworm.dls.bestellung.v03.user;

import org.junit.Test;

import javax.xml.bind.JAXB;
import java.io.InputStream;

public class DlsBooksResponseTest {

    @Test
    public void shouldUnmarshalBooksStatusResponse() {
        final InputStream resourceAsStream = DlsBooksResponseTest.class.getResourceAsStream("/dls/0.3/DlsBooksStatusResponse.xml");
        final DlsBooksResponse unmarshal = JAXB.unmarshal(resourceAsStream, DlsBooksResponse.class);
        System.out.println(unmarshal);
    }

}
