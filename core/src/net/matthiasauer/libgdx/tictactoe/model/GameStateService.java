package net.matthiasauer.libgdx.tictactoe.model;

import java.util.Observer;

/**
 * Created by Matthias on 08/12/2016.
 */
public interface GameStateService extends Observer {
    GameState getGameState();
}
