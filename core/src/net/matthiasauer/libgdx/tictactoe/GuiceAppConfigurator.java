package net.matthiasauer.libgdx.tictactoe;

import com.github.czyzby.kiwi.util.tuple.TripleTuple;
import com.github.czyzby.kiwi.util.tuple.immutable.Pair;
import com.google.inject.AbstractModule;
import com.google.inject.binder.ScopedBindingBuilder;

import javax.inject.Singleton;

/**
 * Guice retrieves the mappings from this class
 */
public class GuiceAppConfigurator extends AbstractModule {
    @Override
    protected void configure() {
        for (TripleTuple<Class, Class, Boolean> injectable : RootConfigurator.getInjectables()) {

            // bind the implementation to the interface
            ScopedBindingBuilder builder = bind(injectable.getFirst()).to(injectable.getSecond());

            // if desired - make sure that only one instance of the class exists at any time
            if (injectable.getThird()) {
                builder.in(Singleton.class);
            }
        }
    }
}
