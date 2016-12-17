package net.matthiasauer.libgdx.tictactoe.model;

import net.matthiasauer.libgdx.tictactoe.utils.GdxObserver;

/**
 * Created by Matthias on 08/12/2016.
 */
public interface GameStateService extends GdxObserver {
    GameState getGameState();

    void initialize();
}
