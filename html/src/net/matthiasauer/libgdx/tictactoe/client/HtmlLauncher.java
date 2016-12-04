package net.matthiasauer.libgdx.tictactoe.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.google.gwt.core.client.GWT;
import net.matthiasauer.libgdx.tictactoe.GinAppInjector;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(480, 320);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                // create the injector
                GinAppInjector ginjector = GWT.create(GinAppInjector.class);

                return ginjector.getApplicationListener();
        }
}