package eu.artofcoding.bookworm.dls.bestellung.v03.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class OrderController {

    private final PlaceBilletService placeBilletService;

    private final BilletStatusService billetStatusService;

    @Autowired
    public OrderController(PlaceBilletService placeBilletService, BilletStatusService billetStatusService) {
        this.placeBilletService = placeBilletService;
        this.billetStatusService = billetStatusService;
    }

    @RequestMapping(value = "/billet/place/{userId}/{aghNummer}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BookOrder> placeBillet(@PathVariable("userId") String userId, @PathVariable("aghNummer") String aghNummer) {
        final BookOrder bestellung = placeBilletService.invoke(userId, aghNummer);
        if (null != bestellung) {
            return new ResponseEntity<>(bestellung, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/billet/status/{userId}/{aghNummer}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> billetStatus(@PathVariable("userId") String userId, @PathVariable("aghNummer") String aghNummer) {
        final String status = billetStatusService.invoke(userId, aghNummer);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
