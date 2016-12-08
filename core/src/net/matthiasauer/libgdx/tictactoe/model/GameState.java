package net.matthiasauer.libgdx.tictactoe.model;

/**
 * Created by Matthias on 08/12/2016.
 */
public enum GameState {
    UNDECIDED(false, Owner.None),
    WON_BY_CROSS(true, Owner.Cross),
    WON_BY_CIRCLE(true, Owner.Circle),
    WON_BY_NOONE(true, Owner.None);

    public boolean isGameOver;
    public Owner winner;

    GameState(boolean isGameOver, Owner winner) {
        this.isGameOver = isGameOver;
        this.winner = winner;
    }
}
