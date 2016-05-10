package eu.artofcoding.bookworm.dls.bestellung.v03.book;

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
public class BookController {

    private final BlistaBookStatus blistaBookStatus;

    private final AvailableBooks availableBooks;

    @Autowired
    public BookController(BlistaBookStatus blistaBookStatus, AvailableBooks availableBooks) {
        this.blistaBookStatus = blistaBookStatus;
        this.availableBooks = availableBooks;
    }

    @RequestMapping(value = "/book/available/{aghNummer}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BookAvailability> bookAvailable(@PathVariable("aghNummer") String aghNummer) {
        final boolean available = availableBooks.findBook(aghNummer);
        return new ResponseEntity<>(new BookAvailability(aghNummer, available), HttpStatus.OK);
    }

    @RequestMapping(value = "/book/status/{userId}/{aghNummer}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BookOrderStatus> bookStatus(@PathVariable("userId") String userId, @PathVariable("aghNummer") String aghNummer) {
        return new ResponseEntity<>(blistaBookStatus.bookStatus(userId, aghNummer), HttpStatus.OK);
    }

}
