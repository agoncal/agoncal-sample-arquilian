package org.agoncal.sample.arquilian.persistence;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
@Stateless
@DataSourceDefinition(name = "java:global/jdbc/sampleArquilianPersistenceDS",
        className = "org.apache.derby.jdbc.EmbeddedDriver",
        url = "jdbc:derby:memory:sampleArquilianWytiwyrDB;create=true;user=app;password=app"
)
public class ItemEJB {

    // ======================================
    // =             Attributes             =
    // ======================================

    @PersistenceContext(name = "sampleArquilianPersistencePU")
    private EntityManager em;

    @Inject
    private IsbnGenerator numberGenerator;

    // ======================================
    // =          Business methods          =
    // ======================================

    public Book createBook(Book book) {
        book.setIsbn(numberGenerator.generateNumber());
        em.persist(book);
        return book;
    }

    public void removeBook(Book book) {
        em.remove(em.merge(book));
    }

    public List<Book> findAllScifiBooks() {
        return em.createNamedQuery(Book.FIND_ALL_SCIFI, Book.class).getResultList();
    }
}