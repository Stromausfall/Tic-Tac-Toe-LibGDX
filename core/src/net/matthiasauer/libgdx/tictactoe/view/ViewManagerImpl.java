package net.matthiasauer.libgdx.tictactoe.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Matthias on 17/12/2016.
 */
public class ViewManagerImpl implements ViewManager {
    private SpriteBatch batch;

    public void initialize() {
        batch = new SpriteBatch();
    }

    public void render() {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //batch.draw(img, 0, 0);
        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }
}
