package eu.artofcoding.bookworm.dls.bestellung.v03.book;

import eu.artofcoding.bookworm.dls.bestellung.config.AppConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class AvailableBooksTest {

    @Autowired
    private AvailableBooks availableBooks;

    @Test @Ignore
    public void shouldFindBook() throws Exception {
        assertTrue(availableBooks.findBook("1-0079399-1-5"));
    }

}
