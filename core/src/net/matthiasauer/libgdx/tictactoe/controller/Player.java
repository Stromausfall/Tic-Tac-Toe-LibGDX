package net.matthiasauer.libgdx.tictactoe.controller;

import net.matthiasauer.libgdx.tictactoe.model.TileManager;

/**
 * Created by Matthias on 08/12/2016.
 */
public interface Player {
    void initialize(TileManager tileManager);
    void startTurn();
    void tileClicked(int x, int y);
    boolean isTurnFinished();
}
