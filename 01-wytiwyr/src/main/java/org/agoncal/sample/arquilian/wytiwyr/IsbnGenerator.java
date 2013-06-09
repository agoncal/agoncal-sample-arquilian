package org.agoncal.sample.arquilian.wytiwyr;

import javax.inject.Inject;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class IsbnGenerator {

    @Inject
    private Logger logger;

    // ======================================
    // =          Business methods          =
    // ======================================

    public String generateNumber() {
        logger.info("generate");
        return "13-84356-" + Math.abs(new Random().nextInt());
    }

    // ======================================
    // = Getters & Setters when Mock testing=
    // ======================================

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}