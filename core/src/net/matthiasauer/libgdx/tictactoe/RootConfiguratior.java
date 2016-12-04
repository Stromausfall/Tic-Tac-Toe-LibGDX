package net.matthiasauer.libgdx.tictactoe;

import com.badlogic.gdx.ApplicationListener;
import com.github.czyzby.kiwi.util.tuple.immutable.Pair;

import java.util.LinkedList;
import java.util.List;

/**
 * Contains the mappings of interfaces to their implemenations used for DI
 */
public class RootConfiguratior {
    private static List<Pair<Class, Class>> injectables = null;

    private static List<Pair<Class, Class>> createInjectables() {
        List<Pair<Class, Class>> data = new LinkedList<Pair<Class, Class>>();

        data.add(new Pair<Class, Class>(ApplicationListener.class, MyGdxGame.class));
        data.add(new Pair<Class, Class>(Foo.class, FooImplementation.class));

        return data;
    }

    public static List<Pair<Class, Class>> getInjectables() {
        if (injectables == null) {
            injectables = createInjectables();
        }

        return injectables;
    }
}
