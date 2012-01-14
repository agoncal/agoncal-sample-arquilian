package org.agoncal.sample.arquilian.wytiwyr;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 */
@Stateless
@Path("/items")
@DataSourceDefinition(name = "java:global/jdbc/sampleArquilianWytiwyrDS",
        className = "org.apache.derby.jdbc.EmbeddedDriver",
        url = "jdbc:derby:memory:sampleArquilianWytiwyrDB;create=true;user=app;password=app"
)
public class ItemEJB {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private EntityManager em;

    @Inject
    private IsbnGenerator numberGenerator;

    @Inject
    private Logger logger;

    // ======================================
    // =          Business methods          =
    // ======================================

    public Book createBook(Book book) {
        logger.info("###### createBook " + book);
        book.setIsbn(numberGenerator.generateNumber());
        em.persist(book);
        return book;
    }

    public void removeBook(Book book) {
        logger.info("###### removeBook " + book);
        em.remove(em.merge(book));
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Book> findAllScifiBooks() {
        logger.info("###### findAllScifiBooks ");
        return em.createNamedQuery(Book.FIND_ALL_SCIFI, Book.class).getResultList();
    }

    // ======================================
    // = Getters & Setters when Mock testing=
    // ======================================

    /**
     * All these setters are needed for unit testing with Mockito (we need to set the mocked
     * entity manager, query and IsbnGenerator)
     */

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setNumberGenerator(IsbnGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }
}