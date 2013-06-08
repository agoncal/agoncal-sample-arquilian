package org.agoncal.sample.arquilian.weldse;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class HelloTest {

    @Test
    public void shouldDisplayHelloWorld() {
        World world = new World();
        assertEquals("should say World !!!", "World !!!", world.sayWorld());
    }
}
