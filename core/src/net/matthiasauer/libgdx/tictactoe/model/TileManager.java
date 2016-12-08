package net.matthiasauer.libgdx.tictactoe.model;

/**
 * Created by Matthias on 08/12/2016.
 */
public interface TileManager {
    /**
     * Initializes the TileManager to be empty
     */
    void initialize();

    /**
     * Returns the tile with the given coordinates
     * @param x
     * @param y
     * @return
     */
    Tile get(int x, int y);
}
