package org.agoncal.sample.arquilian.persistence;

import java.util.Random;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class IsbnGenerator {

    // ======================================
    // =          Business methods          =
    // ======================================

    public String generateNumber() {
        return "13-84356-" + Math.abs(new Random().nextInt());
    }
}