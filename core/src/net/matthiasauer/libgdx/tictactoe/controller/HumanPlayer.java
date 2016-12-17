package net.matthiasauer.libgdx.tictactoe.controller;

import net.matthiasauer.libgdx.tictactoe.model.Owner;
import net.matthiasauer.libgdx.tictactoe.model.Tile;
import net.matthiasauer.libgdx.tictactoe.model.TileManager;

/**
 * Created by Matthias on 10/12/2016.
 */
public class HumanPlayer implements Player {
    private final Owner owner;
    private TileManager tileManager;
    private boolean turnFinished;

    public HumanPlayer(Owner owner) {
        this.turnFinished = false;
        this.owner = owner;
    }

    @Override
    public void initialize(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    @Override
    public void startTurn() {
        this.turnFinished = false;
    }

    @Override
    public void tileClicked(int x, int y) {
        Tile tile = this.tileManager.get(x, y);

        // if the tile is free
        if (tile.getOwner() == Owner.None) {
            // change the owner and make sure the turn has finished
            this.turnFinished = true;
            tile.setOwner(this.owner);
        }
    }

    @Override
    public boolean isTurnFinished() {
        return this.turnFinished;
    }
}
