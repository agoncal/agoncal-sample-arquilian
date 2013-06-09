package org.agoncal.sample.arquilian.wytiwyr;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.validation.ConstraintViolationException;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
@RunWith(Arquillian.class)
@Ignore
public class ItemEJBWithArquillianIT {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private ItemEJB itemEJB;
    private static Long millis = new Date().getTime();

    // ======================================
    // =          Lifecycle Methods         =
    // ======================================

    @Deployment
    public static JavaArchive createTestArchive() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class)
                .addPackage(Book.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
        return archive;
    }

    @AfterClass
    public static void endTimingTest() {
        System.out.println(">>> Integration test ItemEJBWithArquillianIT took : " + (new Date().getTime() - millis) + " millis");
    }

    // ======================================
    // =              Unit tests            =
    // ======================================

    @Test
    public void shouldFindAllScifiBooks() throws Exception {

        // Check JNDI dependencies
        Context ctx = new InitialContext();
        assertNotNull(ctx.lookup("java:global/jdbc/sampleArquilianWytiwyrDS"));

        // Finds all the scifi books
        int initialNumberOfScifiBooks = itemEJB.findAllScifiBooks().size();

        // Creates the books
        Book scifiBook = new Book("Scifi book", 12.5f, "Should fill the query", 345, false, "English", "scifi");
        Book itBook = new Book("Non scifi book", 42.5f, "Should not fill the query", 457, false, "English", "it");
        Book nullBook = new Book(null, 12.5f, "Null title should fail", 457, true, "English", "scifi");

        // Persists the books
        itemEJB.createBook(scifiBook);
        itemEJB.createBook(itBook);
        try {
            itemEJB.createBook(nullBook);
            fail("should not persist a book with a null title");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof ConstraintViolationException);
        }

        // Finds all the scifi books again and make sure there is an extra one
        assertEquals("Should have one extra scifi book", initialNumberOfScifiBooks + 1, itemEJB.findAllScifiBooks().size());

        // Deletes the books
        itemEJB.removeBook(scifiBook);
        itemEJB.removeBook(itBook);

        // Finds all the scifi books again and make sure we have the same initial numbers
        assertEquals("Should have initial number of scifi books", initialNumberOfScifiBooks, itemEJB.findAllScifiBooks().size());
    }
}
