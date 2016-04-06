package eu.artofcoding.bookworm.dls.v03.bestellung;

import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class DlsBooksResponseTest {

    private static final JAXBContext JAXB_CONTEXT;

    static {
        try {
            JAXB_CONTEXT = JAXBContext.newInstance(DlsBooksResponse.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldUnmarshalBookStatusResponse() {
        final InputStream resourceAsStream = DlsBooksResponseTest.class.getResourceAsStream("/dls/0.3/DlsBooksStatusResponse.xml");
        try {
            final Unmarshaller unmarshaller = JAXB_CONTEXT.createUnmarshaller();
            final DlsBooksResponse unmarshalledObject = (DlsBooksResponse) unmarshaller.unmarshal(resourceAsStream);
            System.out.println(unmarshalledObject);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
