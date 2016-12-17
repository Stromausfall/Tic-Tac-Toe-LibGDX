package net.matthiasauer.libgdx.tictactoe;

import com.github.czyzby.kiwi.util.tuple.TripleTuple;
import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.gwt.inject.client.binder.GinScopedBindingBuilder;

import javax.inject.Singleton;

/**
 * Gin retrieves the mappings from this class
 */
public class GinAppConfigurator implements GinModule {
    @Override
    public void configure(GinBinder binder) {
        for (TripleTuple<Class, Class, Boolean> injectable : RootConfigurator.getInjectables()) {

            // bind the implementation to the interface
            GinScopedBindingBuilder builder = binder.bind(injectable.getFirst()).to(injectable.getSecond());

            // if desired - make sure that only one instance of the class exists at any time
            if (injectable.getThird()) {
                builder.in(Singleton.class);
            }
        }
    }
}
