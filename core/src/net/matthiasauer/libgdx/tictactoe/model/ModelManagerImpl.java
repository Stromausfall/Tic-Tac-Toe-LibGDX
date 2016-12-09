package net.matthiasauer.libgdx.tictactoe.model;

import javax.inject.Inject;

/**
 * Created by Matthias on 08/12/2016.
 */
public class ModelManagerImpl implements ModelManager {
    private GameStateService gameStateService;
    private TileManager tileManager;

    @Inject
    public ModelManagerImpl(GameStateService gameStateService, TileManager tileManager) {
        this.gameStateService = gameStateService;
        this.tileManager = tileManager;
    }

    @Override
    public void initialize() {
        this.gameStateService.initialize();
        this.tileManager.initialize();
    }
}
