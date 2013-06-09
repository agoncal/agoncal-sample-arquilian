package org.agoncal.sample.arquilian.wytiwyr;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
@Singleton
@Startup
public class DatabasePopulator {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private ItemEJB itemEJB;

    @Inject
    private Logger logger;

    // ======================================
    // =          Lifecycle Methods         =
    // ======================================

    @PostConstruct
    private void populateDB() {

        itemEJB.createBook(new Book("H2G2", 12.5f, "Best IT Scifi Book", 457, false, "English", "scifi"));
        itemEJB.createBook(new Book("Harry Potter and the Goblet of Fire", 19.79f, "Science fiction (Book 4)", 734, true, "English", "scifi"));
        itemEJB.createBook(new Book("The Elements of Style", 6.64f, "A masterpiece in the art of clear and concise writing.", 105, false, "English", "it"));
        itemEJB.createBook(new Book("The Difference Between God and Larry Ellison", 11.99f, "God Doesn't Think He's Larry Ellison", 420, false, "English", "scifi"));
        itemEJB.createBook(new Book("The Da Vinci Code", 17.79f, "Thriller", 454, false, "English", "thriller"));

        logger.info("######### Inserted " + itemEJB.findAllScifiBooks().size() + " Books");
    }
}
