package net.matthiasauer.libgdx.tictactoe.view;

import net.matthiasauer.libgdx.tictactoe.controller.ControllerManager;

/**
 * Created by Matthias on 17/12/2016.
 */
public interface ViewManager {
    void initialize(ControllerManager controllerManager);
    void render();
    void dispose();
}
