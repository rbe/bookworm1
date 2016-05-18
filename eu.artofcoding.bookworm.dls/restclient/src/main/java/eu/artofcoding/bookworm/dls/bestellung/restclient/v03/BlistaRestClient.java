package eu.artofcoding.bookworm.dls.bestellung.restclient.v03;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class BlistaRestClient implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlistaRestClient.class);

    private static final String URL = "http://localhost:8095";

    public static void main(String[] args) {
        final BlistaRestClient blistaRestClient = new BlistaRestClient();
//        final boolean bookAvailable = blistaRestClient.bookAvailable("1-0000122-3-9");
//        System.out.println(bookAvailable);
//        final BookOrder placeBillet = blistaRestClient.placeBillet("titusTest", "1-0000122-3-9");
//        System.out.println(placeBillet);
//        final String billetStatus = blistaRestClient.billetStatus("titusTest", "1-0000122-3-9");
//        System.out.println(billetStatus);
//        final BookOrderStatus bookStatus = blistaRestClient.bookStatus("titusTest", "1-0000122-3-9");
//        System.out.println(bookStatus);
//        final UserOrderStatus userOrderStatus = blistaRestClient.userStatus("titusTest");
//        System.out.println(userOrderStatus);
    }

    @SuppressWarnings({"unchecked"})
    public boolean bookAvailable(final String aghNummer) {
        if (null != aghNummer && !aghNummer.trim().isEmpty()) {
            final Client client = Client.create();
            final WebResource webResource = client.resource(String.format("%s/book/available/%s", URL, aghNummer));
            final BookAvailability bookAvailability = webResource.accept("application/xml").get(BookAvailability.class);
            try {
            return bookAvailability.isAvailable();
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    @SuppressWarnings({"unchecked"})
    public BookOrder placeBillet(final String userId, final String aghNummer) {
        final Client client = Client.create();
        final WebResource webResource = client.resource(String.format("%s/billet/place/%s/%s", URL, userId, aghNummer));
        try {
        return webResource.accept("application/xml").get(BookOrder.class);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings({"unchecked"})
    public String billetStatus(final String userId, final String aghNummer) {
        final Client client = Client.create();
        final WebResource webResource = client.resource(String.format("%s/billet/status/%s/%s", URL, userId, aghNummer));
        try {
        return webResource.accept("application/json").get(String.class);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings({"unchecked"})
    public BookOrderStatus bookStatus(final String userId, final String aghNummer) {
        final Client client = Client.create();
        final WebResource webResource = client.resource(String.format("%s/book/status/%s/%s", URL, userId, aghNummer));
        try {
        return webResource.accept("application/xml").get(BookOrderStatus.class);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings({"unchecked"})
    public UserOrderStatus userStatus(final String userId) {
        final Client client = Client.create();
        final WebResource webResource = client.resource(String.format("%s/book/status/%s", URL, userId));
        try {
            return webResource.accept("application/xml").get(UserOrderStatus.class);
        } catch (Exception e) {
            return null;
        }
    }

}
