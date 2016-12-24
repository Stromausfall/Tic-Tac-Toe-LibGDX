package net.matthiasauer.libgdx.tictactoe.controller;

import com.badlogic.gdx.utils.PooledLinkedList;
import com.github.czyzby.kiwi.util.gdx.collection.GdxLists;
import net.matthiasauer.libgdx.tictactoe.model.GameState;
import net.matthiasauer.libgdx.tictactoe.model.GameStateService;
import net.matthiasauer.libgdx.tictactoe.model.ModelManager;
import net.matthiasauer.libgdx.tictactoe.model.TileManager;
import net.matthiasauer.libgdx.tictactoe.view.ViewManager;

import javax.inject.Inject;

/**
 * Created by Matthias on 08/12/2016.
 */
public class ControllerManagerImpl implements ControllerManager {
    private final TileManager tileManager;
    private final ModelManager modelManager;
    private final ViewManager viewManager;
    private final GameStateService gameStateService;
    private final PooledLinkedList<Player> players;

    private Player getCurrentPlayer() {
        this.players.iter();

        return this.players.next();
    }

    @Inject
    public ControllerManagerImpl(ModelManager modelManager, GameStateService gameStateService, TileManager tileManager, ViewManager viewManager) {
        this.modelManager = modelManager;
        this.gameStateService = gameStateService;
        this.tileManager = tileManager;
        this.viewManager = viewManager;
        this.players = GdxLists.newList(2);
    }

    @Override
    public void initialize(Player player1, Player player2) {
        this.modelManager.initialize();
        this.players.add(player1);
        this.players.add(player2);

        // initialize the players
        player1.initialize(this.tileManager);
        player2.initialize(this.tileManager);

        this.startCurrentPlayersTurn();
        this.viewManager.initialize(this);
    }

    private void startCurrentPlayersTurn() {
        this.gameStateService.update();

        if (this.gameStateService.getGameState() == GameState.UNDECIDED) {
            this.getCurrentPlayer().startTurn();
            this.checkIfTurnFinished();
        }
    }

    private void checkIfTurnFinished() {
        boolean turnFinished = this.getCurrentPlayer().isTurnFinished();

        if (turnFinished) {
            // get the current player
            Player currentPlayer = this.getCurrentPlayer();

            // remove it from the front of the list
            this.players.remove();

            // and reattach it at the end
            this.players.add(currentPlayer);

            // also check if the new player's turn has finished
            this.startCurrentPlayersTurn();
        }
    }

    @Override
    public void tileClicked(int x, int y) {
        if (this.gameStateService.getGameState() == GameState.UNDECIDED) {
            this.getCurrentPlayer().tileClicked(x, y);

            this.checkIfTurnFinished();
        }
    }
}
