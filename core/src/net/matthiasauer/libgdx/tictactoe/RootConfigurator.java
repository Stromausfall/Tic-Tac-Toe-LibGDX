package net.matthiasauer.libgdx.tictactoe;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Input;
import com.github.czyzby.kiwi.util.tuple.TripleTuple;
import com.github.czyzby.kiwi.util.tuple.immutable.Triple;
import net.matthiasauer.libgdx.tictactoe.controller.ControllerManager;
import net.matthiasauer.libgdx.tictactoe.controller.ControllerManagerImpl;
import net.matthiasauer.libgdx.tictactoe.model.*;
import net.matthiasauer.libgdx.tictactoe.view.InputHandler;
import net.matthiasauer.libgdx.tictactoe.view.InputHandlerImpl;
import net.matthiasauer.libgdx.tictactoe.view.ViewManager;
import net.matthiasauer.libgdx.tictactoe.view.ViewManagerImpl;

import java.util.LinkedList;
import java.util.List;

/**
 * Contains the mappings of interfaces to their implemenations used for DI
 */
public class RootConfigurator {
    private static List<TripleTuple<Class, Class, Boolean>> injectables = null;

    private static List<TripleTuple<Class, Class, Boolean>> createInjectables() {
        List<TripleTuple<Class, Class, Boolean>> data = new LinkedList<TripleTuple<Class, Class, Boolean>>();

        add(data, ApplicationListener.class, MyGdxGame.class, true);
        add(data, ControllerManager.class, ControllerManagerImpl.class, true);
        add(data, ModelManager.class, ModelManagerImpl.class, true);
        add(data, GameStateService.class, GameStateServiceImpl.class, true);
        add(data, TileManager.class, TileManagerImpl.class, true);
        add(data, ViewManager.class, ViewManagerImpl.class, true);
        add(data, InputHandler.class, InputHandlerImpl.class, true);

        return data;
    }

    private static void add(List<TripleTuple<Class, Class, Boolean>> data, Class<?> clazz1, Class<?> clazz2, boolean singleton) {
        data.add(new Triple<Class, Class, Boolean>(clazz1, clazz2, singleton));
    }

    public static List<TripleTuple<Class, Class, Boolean>> getInjectables() {
        if (injectables == null) {
            injectables = createInjectables();
        }

        return injectables;
    }
}
