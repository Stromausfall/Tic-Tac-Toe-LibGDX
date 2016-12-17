package net.matthiasauer.libgdx.tictactoe.model;

import com.badlogic.gdx.utils.ObjectSet;
import com.github.czyzby.kiwi.util.gdx.collection.GdxSets;
import net.matthiasauer.libgdx.tictactoe.utils.GdxObservable;

import javax.inject.Inject;

/**
 * Created by Matthias on 08/12/2016.
 */
public class GameStateServiceImpl extends GdxObservable implements GameStateService {
    private final TileManager tileManager;
    private final ObjectSet<Owner> winDetectionHelper;
    private GameState gameState;

    @Inject
    public GameStateServiceImpl(TileManager tileManager) {
        this.tileManager = tileManager;
        this.winDetectionHelper = GdxSets.newSet();
        this.gameState = GameState.UNDECIDED;
    }

    @Override
    public GameState getGameState() {
        return this.gameState;
    }

    @Override
    public void initialize() {
        this.gameState = GameState.UNDECIDED;

        this.notifyObservers();
    }

    @Override
    public void update() {
        this.checkIfHorizontalWin();
        this.checkIfVerticalWin();
        this.checkIfDiagonalWinVariant1();
        this.checkIfDiagonalWinVariant2();
        this.checkIfNoPlayerWon();
    }

    private void addOwnerToWinDetectionHelper(int x, int y) {
        Tile tile = this.tileManager.get(x, y);
        Owner owner = tile.getOwner();

        this.winDetectionHelper.add(owner);
    }

    private void checkIfNoPlayerWon() {
        this.winDetectionHelper.clear();

        for (int x = 0; x < Constants.SIDE_TILE_COUNT; x++) {
            for (int y = 0; y < Constants.SIDE_TILE_COUNT; y++) {
                this.addOwnerToWinDetectionHelper(x, y);
            }
        }

        // if no tile is owned by Owner.None then the game is to be terminated !
        if (!this.winDetectionHelper.contains(Owner.None)) {

            // if the game has already been terminated
            if (this.gameState != GameState.UNDECIDED) {
                return;
            }

            this.notifyObservers();
        }
    }

    private void checkIfHorizontalWin() {
        // across all rows
        for (int y = 0; y < Constants.SIDE_TILE_COUNT; y++) {
            this.winDetectionHelper.clear();

            // for each row
            for (int x = 0; x < Constants.SIDE_TILE_COUNT; x++) {
                this.addOwnerToWinDetectionHelper(x, y);
            }

            this.evaluateWinDetectionHelper();
        }
    }

    private void checkIfVerticalWin() {
        // across all rows
        for (int x = 0; x < Constants.SIDE_TILE_COUNT; x++) {
            this.winDetectionHelper.clear();

            // for each column
            for (int y = 0; y < Constants.SIDE_TILE_COUNT; y++) {
                this.addOwnerToWinDetectionHelper(x, y);
            }

            this.evaluateWinDetectionHelper();
        }
    }

    private void checkIfDiagonalWinVariant1() {
        this.winDetectionHelper.clear();

        // variant 1
        // X - -
        // - X -
        // - - X
        for (int i = 0; i < Constants.SIDE_TILE_COUNT; i++) {
            this.addOwnerToWinDetectionHelper(i, i);
        }

        this.evaluateWinDetectionHelper();
    }

    private void checkIfDiagonalWinVariant2() {
        this.winDetectionHelper.clear();

        // variant 2
        // - - X
        // - X -
        // X - -
        for (int i = 0; i < Constants.SIDE_TILE_COUNT; i++) {
            this.addOwnerToWinDetectionHelper(i, 2 - i);

        }

        this.evaluateWinDetectionHelper();
    }

    private void evaluateWinDetectionHelper() {
        // if the game has already been terminated
        if (this.gameState != GameState.UNDECIDED) {
            return;
        }

        // there was only one kind of owner for all tiles
        if (this.winDetectionHelper.size == 1) {
            Owner potentialWinner = this.winDetectionHelper.first();

            // depending on the owner - decide the game state
            switch (potentialWinner) {
                case Circle:
                    this.gameState = GameState.WON_BY_CIRCLE;
                    break;
                case Cross:
                    this.gameState = GameState.WON_BY_CROSS;
                    break;
                default:
                    break;
            }
        }

        // only notify observers if the game state has changed
        if (this.gameState != GameState.UNDECIDED) {
            this.notifyObservers();
        }
    }

}
