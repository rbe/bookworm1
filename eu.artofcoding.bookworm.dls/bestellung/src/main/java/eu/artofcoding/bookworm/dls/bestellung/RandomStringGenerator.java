package eu.artofcoding.bookworm.dls.bestellung;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;

@Component
public class RandomStringGenerator {

    private SecureRandom random = new SecureRandom();

    public String next() {
        return new BigInteger(130, random).toString(32);
    }

}
