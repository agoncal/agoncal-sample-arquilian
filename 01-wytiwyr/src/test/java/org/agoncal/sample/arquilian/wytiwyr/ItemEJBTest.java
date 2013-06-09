package org.agoncal.sample.arquilian.wytiwyr;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
@RunWith(MockitoJUnitRunner.class)
public class ItemEJBTest {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Mock
    private EntityManager mockedEntityManager;
    @Mock
    private TypedQuery mockedQuery;
    private ItemEJB itemEJB;
    private static Long millis = new Date().getTime();

    // ======================================
    // =          Lifecycle Methods         =
    // ======================================

    @Before
    public void initDependencies() throws Exception {
        IsbnGenerator isbnGenerator = new IsbnGenerator();
        isbnGenerator.setLogger(Logger.getLogger(IsbnGenerator.class.getName()));
        itemEJB = new ItemEJB();
        itemEJB.setEntityManager(mockedEntityManager);
        itemEJB.setNumberGenerator(isbnGenerator);
        itemEJB.setLogger(Logger.getLogger(ItemEJB.class.getName()));
    }

    @AfterClass
    public static void endTimingTest() {
        System.out.println(">>> Unit test ItemEJBTest took : " + (new Date().getTime() - millis) + " millis");
    }

    // ======================================
    // =              Unit tests            =
    // ======================================

    @Test
    public void shouldFindAllScifiBooks() throws Exception {

        List<Book> books = new ArrayList<Book>();

        // Finds all the scifi books
        when(mockedEntityManager.createNamedQuery(Book.FIND_ALL_SCIFI, Book.class)).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(books);
        int initialNumberOfScifiBooks = itemEJB.findAllScifiBooks().size();

        // Creates the books
        Book scifiBook = new Book("Scifi book", 12.5f, "Should fill the query", 345, false, "English", "scifi");
        Book itBook = new Book("Non scifi book", 42.5f, "Should not fill the query", 457, false, "English", "it");
        Book nullBook = new Book(null, 12.5f, "Null title should fail", 457, true, "English", "scifi");

        // Persists the books
        itemEJB.createBook(scifiBook);
        itemEJB.createBook(itBook);
        verify(mockedEntityManager, times(2)).persist(any());

        try {
            doThrow(ConstraintViolationException.class).when(mockedEntityManager).persist(nullBook);
            itemEJB.createBook(nullBook);
            fail("should not persist a book with a null title");
        } catch (ConstraintViolationException e) {
        }

        // Finds all the scifi books again and make sure there is an extra one
        books.add(scifiBook);
        when(mockedQuery.getResultList()).thenReturn(books);
        assertEquals("Should have one extra scifi book", initialNumberOfScifiBooks + 1, itemEJB.findAllScifiBooks().size());

        // Deletes the books
        itemEJB.removeBook(scifiBook);
        itemEJB.removeBook(itBook);
        verify(mockedEntityManager, times(2)).remove(any());

        // Finds all the scifi books again and make sure we have the same initial numbers
        books.remove(scifiBook);
        when(mockedQuery.getResultList()).thenReturn(books);
        assertEquals("Should have initial number of scifi books", initialNumberOfScifiBooks, itemEJB.findAllScifiBooks().size());
    }
}
