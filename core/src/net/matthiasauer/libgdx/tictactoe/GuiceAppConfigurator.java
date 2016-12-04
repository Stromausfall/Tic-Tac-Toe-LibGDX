package net.matthiasauer.libgdx.tictactoe;

import com.github.czyzby.kiwi.util.tuple.immutable.Pair;
import com.google.inject.AbstractModule;

/**
 * Guice retrieves the mappings from this class
 */
public class GuiceAppConfigurator extends AbstractModule {
    @Override
    protected void configure() {
        for (Pair<Class, Class> injectable : RootConfiguratior.getInjectables()) {
            bind(injectable.getFirst()).to(injectable.getSecond());
        }
    }
}
