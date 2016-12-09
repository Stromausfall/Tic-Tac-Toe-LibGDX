package net.matthiasauer.libgdx.tictactoe.controller;

import com.badlogic.gdx.utils.PooledLinkedList;
import com.github.czyzby.kiwi.util.gdx.collection.GdxLists;
import net.matthiasauer.libgdx.tictactoe.model.ModelManager;

import javax.inject.Inject;

/**
 * Created by Matthias on 08/12/2016.
 */
public class ControllerManagerImpl implements ControllerManager {
    private final ModelManager modelManager;
    private final PooledLinkedList<Player> players;

    @Inject
    public ControllerManagerImpl(ModelManager modelManager) {
        this.modelManager = modelManager;
        this.players = GdxLists.newList(2);
    }

    @Override
    public void initialize(Player player1, Player player2) {
        this.modelManager.initialize();
        this.players.add(player1);
        this.players.add(player2);


        this.players.iter();
        this.players.next().startTurn();
    }
}
