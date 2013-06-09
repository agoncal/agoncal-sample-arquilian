package org.agoncal.sample.arquilian.weldse;

import javax.inject.Inject;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class Hello {

    @Inject
    World world;

    public String sayHelloWorld() {
        return "Hello " + world.sayWorld();
    }
}
