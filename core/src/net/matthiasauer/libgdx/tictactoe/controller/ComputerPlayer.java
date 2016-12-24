package net.matthiasauer.libgdx.tictactoe.controller;

import com.badlogic.gdx.math.MathUtils;
import net.matthiasauer.libgdx.tictactoe.model.Owner;
import net.matthiasauer.libgdx.tictactoe.model.Tile;
import net.matthiasauer.libgdx.tictactoe.model.TileManager;

/**
 * Created by Matthias on 11/12/2016.
 */
public class ComputerPlayer implements Player {
    private TileManager tileManager;
    private final Owner owner;

    public ComputerPlayer(Owner owner) {
        this.owner = owner;
    }

    @Override
    public void initialize(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    @Override
    public void startTurn() {
        // get a free tile
        Tile tile = this.getFreeTile();

        // change the owner of the tile
        tile.setOwner(this.owner);
    }

    private Tile getFreeTile() {
        while (true) {
            int x = MathUtils.random(0, 2);
            int y = MathUtils.random(0, 2);
            Tile tile = this.tileManager.get(x, y);

            if (tile.getOwner() == Owner.None) {
                return tile;
            }
        }
    }

    @Override
    public void tileClicked(int x, int y) {
    }

    @Override
    public boolean isTurnFinished() {
        return true;
    }
}
