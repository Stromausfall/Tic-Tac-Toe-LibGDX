package net.matthiasauer.libgdx.tictactoe.view;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Matthias on 18/12/2016.
 */
public interface InputReceiver {
    Rectangle getReceiving();
    void touched();
}
