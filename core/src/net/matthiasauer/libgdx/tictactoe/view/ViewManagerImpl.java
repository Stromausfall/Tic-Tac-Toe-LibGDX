package net.matthiasauer.libgdx.tictactoe.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ObjectSet;
import com.github.czyzby.kiwi.util.gdx.collection.GdxSets;
import net.matthiasauer.libgdx.tictactoe.controller.ControllerManager;
import net.matthiasauer.libgdx.tictactoe.model.Constants;
import net.matthiasauer.libgdx.tictactoe.model.GameState;
import net.matthiasauer.libgdx.tictactoe.model.GameStateService;
import net.matthiasauer.libgdx.tictactoe.model.TileManager;

import javax.inject.Inject;

/**
 * Created by Matthias on 17/12/2016.
 */
public class ViewManagerImpl implements ViewManager {
    private SpriteBatch batch;
    private final GameStateService gameStateService;
    private final TileManager tileManager;
    private final InputHandler inputHandler;
    private final ObjectSet<Tile> tiles;
    private Camera camera;
    private BitmapFont font;

    @Inject
    public ViewManagerImpl(GameStateService gameStateService, TileManager tileManager, InputHandler inputHandler) {
        this.gameStateService = gameStateService;
        this.inputHandler = inputHandler;
        this.tileManager = tileManager;
        this.tiles = GdxSets.newSet();
    }

    public void initialize(ControllerManager controllerManager) {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera(600, 400);
        this.camera.translate(300, 200, 0);
        this.camera.update();

        for (int x = 0; x < Constants.SIDE_TILE_COUNT; x++) {
            for (int y = 0; y < Constants.SIDE_TILE_COUNT; y++) {
                Tile tile = new Tile(x, y, this.tileManager, controllerManager);

                this.inputHandler.add(tile);
                this.tiles.add(tile);
            }
        }

        this.font = new BitmapFont();
        this.font.setColor(Color.RED);
        this.font.getData().setScale(3, 3);
    }

    public void render() {
        // perform input handling
        this.inputHandler.update(this.camera);

        // handle the camera
        this.camera.update();
        batch.setProjectionMatrix(this.camera.combined);

        // white screen
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.batch.begin();

        // render tiles
        for (Tile tile : this.tiles) {
            tile.render(this.batch);
        }

        if (this.gameStateService.getGameState() != GameState.UNDECIDED) {
            this.font.draw(batch, this.gameStateService.getGameState().toString(), 100, 275);
        }

        //batch.draw(img, 0, 0);
        this.batch.end();
    }

    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
    }
}
