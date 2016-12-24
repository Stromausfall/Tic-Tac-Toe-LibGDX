package net.matthiasauer.libgdx.tictactoe.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ObjectSet;
import com.github.czyzby.kiwi.util.gdx.collection.GdxSets;

/**
 * Created by Matthias on 18/12/2016.
 */
public class InputHandlerImpl implements InputHandler {
    private final ObjectSet<InputReceiver> receivers;
    private final Vector3 position = new Vector3();

    public InputHandlerImpl() {
        this.receivers = GdxSets.newSet();
    }

    public void add(InputReceiver receiver) {
        this.receivers.add(receiver);
    }

    public void remove(InputReceiver receiver) {
        this.receivers.remove(receiver);
    }

    public void update(Camera camera) {
        if (Gdx.input.isTouched()) {
            // get the position where the mouse clicked
            this.position.set(
                    Gdx.input.getX(),
                    Gdx.input.getY(),
                    0);

            // change the position from being 'relative to the camera' to
            // 'absolute'
            camera.unproject(this.position);

            float x = this.position.x;
            float y = this.position.y;

            for (InputReceiver receiver : this.receivers) {
                if (receiver.getReceiving().contains(x, y)) {
                    receiver.touched();
                }
            }
        }
    }
}
