package org.agoncal.sample.arquilian.wytiwyr;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class ItemEJBWithoutArquillianIT {

    // ======================================
    // =             Attributes             =
    // ======================================

    private static EJBContainer ec;
    private static Context ctx;
    private static Long millis = new Date().getTime();

    // ======================================
    // =          Lifecycle Methods         =
    // ======================================

    @BeforeClass
    public static void initContainer() throws Exception {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(EJBContainer.MODULES, new File[]{new File("target/classes"), new File("target/test-classes")});
        ec = EJBContainer.createEJBContainer(properties);
        ctx = ec.getContext();
    }

    @AfterClass
    public static void closeContainer() throws Exception {
        if (ec != null) {
            ec.close();
        }
        System.out.println(">>> Integration test ItemEJBWithoutArquillianIT took : " + (new Date().getTime() - millis) + " millis");
    }

    // ======================================
    // =              Unit tests            =
    // ======================================

    @Test
    public void shouldFindAllScifiBooks() throws Exception {

        // Check JNDI dependencies
        assertNotNull(ctx.lookup("java:global/classes/DatabasePopulator"));
        assertNotNull(ctx.lookup("java:global/classes/DatabasePopulator!org.agoncal.sample.arquilian.wytiwyr.DatabasePopulator"));
        assertNotNull(ctx.lookup("java:global/classes/ItemEJB"));
        assertNotNull(ctx.lookup("java:global/classes/ItemEJB!org.agoncal.sample.arquilian.wytiwyr.ItemEJB"));
        assertNotNull(ctx.lookup("java:global/jdbc/sampleArquilianWytiwyrDS"));

        // Looks up for the EJB
        ItemEJB itemEJB = (ItemEJB) ctx.lookup("java:global/classes/ItemEJB");

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
