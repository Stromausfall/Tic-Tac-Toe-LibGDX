package net.matthiasauer.libgdx.tictactoe.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import net.matthiasauer.libgdx.tictactoe.GuiceAppConfigurator;
import net.matthiasauer.libgdx.tictactoe.MyGdxGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        Injector injector = Guice.createInjector(new GuiceAppConfigurator());
        MyGdxGame app = injector.getInstance(MyGdxGame.class);

        new LwjglApplication(app, config);
    }
}
