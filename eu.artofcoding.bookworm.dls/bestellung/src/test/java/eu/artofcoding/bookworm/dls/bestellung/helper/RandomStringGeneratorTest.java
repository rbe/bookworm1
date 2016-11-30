package eu.artofcoding.bookworm.dls.bestellung.helper;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RandomStringGeneratorTest {

    @Test
    public void next() throws Exception {
        for (int i = 0; i < 1_000; i++) {
            final String next = RandomStringGenerator.next();
            assertNotNull(next);
        }
    }

}
