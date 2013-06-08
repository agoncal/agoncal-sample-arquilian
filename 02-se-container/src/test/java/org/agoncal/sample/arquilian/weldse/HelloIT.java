package org.agoncal.sample.arquilian.weldse;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class HelloIT {

    @Inject
    BeanManager beanManager;

    @Inject
    Hello hello;

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class).
                addClass(Hello.class).
                addClass(World.class).
                addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testCdiBootstrap() {
        assertNotNull(beanManager);
        assertFalse(beanManager.getBeans(BeanManager.class).isEmpty());
    }

    @Test
    public void shouldDisplayHelloWorld() {
        assertEquals("should say Hello World !!!", "Hello World !!!", hello.sayHelloWorld());
    }
}
