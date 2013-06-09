package org.agoncal.sample.arquilian.wytiwyr;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         ---
 *         This should work but doesn't in Embbedded GlassFish. As a turn around I've put the
 * @DataSourceDefinition into the EJB class
 */
//@DataSourceDefinition(name = "java:global/jdbc/sampleArquilianWytiwyrDS",
//        className = "org.apache.derby.jdbc.EmbeddedDriver",
//        url = "jdbc:derby:memory:sampleArquilianWytiwyrDB;create=true;user=app;password=app"
//)
public class DatabaseResource {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Produces
    @PersistenceContext(unitName = "sampleArquilianWytiwyrPU")
    private EntityManager em;
}