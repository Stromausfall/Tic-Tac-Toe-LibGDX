package net.matthiasauer.libgdx.tictactoe.controller;

import com.badlogic.gdx.utils.PooledLinkedList;
import com.github.czyzby.kiwi.util.gdx.collection.GdxLists;
import net.matthiasauer.libgdx.tictactoe.model.GameState;
import net.matthiasauer.libgdx.tictactoe.model.GameStateService;
import net.matthiasauer.libgdx.tictactoe.model.ModelManager;

import javax.inject.Inject;

/**
 * Created by Matthias on 08/12/2016.
 */
public class ControllerManagerImpl implements ControllerManager {
    private final ModelManager modelManager;
    private final GameStateService gameStateService;
    private final PooledLinkedList<Player> players;

    private Player getCurrentPlayer() {
        this.players.iter();

        return this.players.next();
    }

    @Inject
    public ControllerManagerImpl(ModelManager modelManager, GameStateService gameStateService) {
        this.modelManager = modelManager;
        this.gameStateService = gameStateService;
        this.players = GdxLists.newList(2);
    }

    @Override
    public void initialize(Player player1, Player player2) {
        this.modelManager.initialize();
        this.players.add(player1);
        this.players.add(player2);

        this.startCurrentPlayersTurn();
    }

    private void startCurrentPlayersTurn() {
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
        this.getCurrentPlayer().tileClicked(x, y);

        this.checkIfTurnFinished();
    }
}
