package net.matthiasauer.libgdx.tictactoe.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import net.matthiasauer.libgdx.tictactoe.controller.ControllerManager;
import net.matthiasauer.libgdx.tictactoe.model.Constants;
import net.matthiasauer.libgdx.tictactoe.model.TileManager;
import net.matthiasauer.libgdx.tictactoe.utils.GdxObserver;

/**
 * Created by Matthias on 18/12/2016.
 */
public class Tile implements InputReceiver {
    private final int x;
    private final int y;
    private Rectangle rectangle;
    private final TileManager tileManager;
    private final ControllerManager controllerManager;
    private Sprite sprite;

    public Tile(int x, int y, TileManager tileManager, ControllerManager controllerManager) {
        this.x = x;
        this.y = y;
        this.tileManager = tileManager;
        this.controllerManager = controllerManager;

        this.updateSprite();
        this.getModel().addObserver(new GdxObserver() {
            @Override
            public void update() {
                Tile.this.updateSprite();
            }
        });
    }

    public void render(SpriteBatch batch) {
        this.sprite.draw(batch);
    }

    private net.matthiasauer.libgdx.tictactoe.model.Tile getModel() {
        return this.tileManager.get(this.x, this.y);
    }

    private String getAssetName() {
        switch (this.getModel().getOwner()) {
            case Circle:
                return "tile_circle.png";
            case Cross:
                return "tile_cross.png";
            default:
                return "tile_empty.png";
        }

    }

    private void updateSprite() {
        // create the texture
        Texture texture = new Texture(this.getAssetName());

        // create the sprite
        this.sprite = new Sprite(texture);

        // position the sprite
        this.sprite.setX(175 + this.x * Constants.TILE_SIZE);
        this.sprite.setY(50 + this.y * Constants.TILE_SIZE);

        this.rectangle = this.sprite.getBoundingRectangle();
    }

    @Override
    public Rectangle getReceiving() {
        return this.rectangle;
    }

    @Override
    public void touched() {
        this.controllerManager.tileClicked(this.x, this.y);
    }
}
