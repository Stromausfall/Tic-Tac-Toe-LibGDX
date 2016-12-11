package net.matthiasauer.libgdx.tictactoe.controller;

/**
 * Created by Matthias on 08/12/2016.
 */
public interface ControllerManager {
    void initialize(Player player1, Player player2);
    void tileClicked(int x, int y);
}
