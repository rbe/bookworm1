package eu.artofcoding.bookworm.dls.v03.bestellung;

import org.junit.Test;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class DlsBookResponseTest {

    @Test
    public void shouldUnmarshalBookStatusResponse() {
        final InputStream resourceAsStream = DlsBookResponseTest.class.getResourceAsStream("/dls/0.3/DlsBookStatusResponse.xml");
        try {
            final Unmarshaller unmarshaller = DlsBookJaxbContext.INSTANCE.createUnmarshaller();
            final Object unmarshal = unmarshaller.unmarshal(resourceAsStream);
            final DlsBookResponse unmarshalledObject = (DlsBookResponse) unmarshal;
            System.out.println(unmarshalledObject);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
