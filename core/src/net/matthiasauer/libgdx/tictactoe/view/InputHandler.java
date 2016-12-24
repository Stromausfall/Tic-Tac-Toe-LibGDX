package net.matthiasauer.libgdx.tictactoe.view;

import com.badlogic.gdx.graphics.Camera;

/**
 * Created by Matthias on 18/12/2016.
 */
public interface InputHandler {
    void add(InputReceiver receiver);

    void remove(InputReceiver receiver);

    void update(Camera camera);
}
