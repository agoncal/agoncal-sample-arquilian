package org.agoncal.sample.arquilian.weldse;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class HelloTest {

    // ======================================
    // =              Unit tests            =
    // ======================================

    @Test
    public void shouldDisplayHelloWorld() {
        World world = new World();
        assertEquals("should say World !!!", "World !!!", world.sayWorld());
    }
}
