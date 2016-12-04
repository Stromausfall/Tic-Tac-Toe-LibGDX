package net.matthiasauer.libgdx.tictactoe;

import com.github.czyzby.kiwi.util.tuple.immutable.Pair;
import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;

/**
 * Gin retrieves the mappings from this class
 */
public class GinAppConfigurator implements GinModule {
    @Override
    public void configure(GinBinder binder) {
        for (Pair<Class, Class> injectable : RootConfiguratior.getInjectables()) {
            binder.bind(injectable.getFirst()).to(injectable.getSecond());
        }
    }
}
