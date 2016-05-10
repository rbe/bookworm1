package eu.artofcoding.bookworm.dls.bestellung.v03.user;

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
public class UserController {

    private final UserStatusService userStatusService;

    @Autowired
    public UserController(UserStatusService userStatusService) {
        this.userStatusService = userStatusService;
    }

    @RequestMapping(value = "/book/status/{userId}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserOrderStatus> userStatus(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(userStatusService.userStatus(userId), HttpStatus.OK);
    }

}
