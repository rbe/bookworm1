package eu.artofcoding.bookworm.dls.v03.bestellung;

import org.junit.Test;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class DlsBooksResponseTest {

    @Test
    public void shouldUnmarshalBooksStatusResponse() {
        final InputStream resourceAsStream = DlsBooksResponseTest.class.getResourceAsStream("/dls/0.3/DlsBooksStatusResponse.xml");
        try {
            final Unmarshaller unmarshaller = DlsBooksJaxbContext.INSTANCE.createUnmarshaller();
            final DlsBooksResponse unmarshalledObject = (DlsBooksResponse) unmarshaller.unmarshal(resourceAsStream);
            System.out.println(unmarshalledObject);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
