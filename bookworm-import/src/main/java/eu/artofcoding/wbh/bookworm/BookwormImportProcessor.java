package eu.artofcoding.wbh.bookworm;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.List;

@Transactional
public class BookwormImportProcessor {

    @PersistenceContext
    private EntityManager em;

    private BookwormFileParser bookwormFileParser;

    public void setBookwormFileParser(BookwormFileParser bookwormFileParser) {
        this.bookwormFileParser = bookwormFileParser;
    }

    public void importFile(File file) throws Exception {
        // Check state
        if (null == bookwormFileParser || null == em) {
            throw new IllegalStateException("No file parser or no entity manager");
        }
        // Truncate table
        em.createNativeQuery("TRUNCATE TABLE books").executeUpdate();
        // Insert data
        List<BookEntity> bookEntities = bookwormFileParser.parseFile(file.toURI());
        for (BookEntity b : bookEntities) {
            em.persist(b);
        }
    }

}
