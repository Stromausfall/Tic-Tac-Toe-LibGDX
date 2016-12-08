package net.matthiasauer.libgdx.tictactoe.model;

import com.badlogic.gdx.utils.ArrayMap;
import com.github.czyzby.kiwi.util.gdx.collection.GdxMaps;
import com.github.czyzby.kiwi.util.tuple.DoubleTuple;
import com.github.czyzby.kiwi.util.tuple.immutable.Pair;
import com.github.czyzby.kiwi.util.tuple.mutable.MutablePair;

/**
 * Created by Matthias on 08/12/2016.
 */
public class TileManagerImpl implements TileManager {
    private MutablePair<Integer, Integer> accessHelper;
    private ArrayMap<DoubleTuple, Tile> tiles;

    public TileManagerImpl() {
        this.accessHelper = new MutablePair<Integer, Integer>(0, 0);
        this.tiles = GdxMaps.newArrayMap(DoubleTuple.class, Tile.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
        for (int x = 0; x < Constants.SIDE_TILE_COUNT; x++) {
            for (int y = 0; y < Constants.SIDE_TILE_COUNT; y++) {
                Pair<Integer, Integer> key = new Pair<Integer, Integer>(x, y);
                Tile value = new Tile();

                this.tiles.put(key, value);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tile get(int x, int y) {
        // change the key used to access tiles
        this.accessHelper.set(0, x);
        this.accessHelper.set(1, y);

        // retrieve the tile at the coordinates
        return this.tiles.get(this.accessHelper);
    }
}
