package eu.artofcoding.bookworm.dls.bestellung.helper;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class RandomStringGenerator {

    private static final SecureRandom random = new SecureRandom();

    private RandomStringGenerator() {
        throw new AssertionError();
    }

    public static String next() {
        return new BigInteger(130, random).toString(32);
    }

}
