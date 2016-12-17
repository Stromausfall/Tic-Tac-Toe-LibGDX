package net.matthiasauer.libgdx.tictactoe;

import com.badlogic.gdx.ApplicationListener;
import com.github.czyzby.kiwi.util.tuple.immutable.Pair;
import net.matthiasauer.libgdx.tictactoe.controller.ControllerManager;
import net.matthiasauer.libgdx.tictactoe.controller.ControllerManagerImpl;
import net.matthiasauer.libgdx.tictactoe.model.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Contains the mappings of interfaces to their implemenations used for DI
 */
public class RootConfigurator {
    private static List<Pair<Class, Class>> injectables = null;

    private static List<Pair<Class, Class>> createInjectables() {
        List<Pair<Class, Class>> data = new LinkedList<Pair<Class, Class>>();

        add(data, ApplicationListener.class, MyGdxGame.class);
        add(data, ControllerManager.class, ControllerManagerImpl.class);
        add(data, ModelManager.class, ModelManagerImpl.class);
        add(data, GameStateService.class, GameStateServiceImpl.class);
        add(data, TileManager.class, TileManagerImpl.class);

        return data;
    }

    private static void add(List<Pair<Class, Class>> data, Class<?> clazz1, Class<?> clazz2) {
        data.add(new Pair<Class, Class>(clazz1, clazz2));
    }

    public static List<Pair<Class, Class>> getInjectables() {
        if (injectables == null) {
            injectables = createInjectables();
        }

        return injectables;
    }
}
