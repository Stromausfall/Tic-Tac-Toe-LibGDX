package net.matthiasauer.libgdx.tictactoe;

import com.badlogic.gdx.ApplicationListener;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

/**
 * This class allows Gin to create the entry class
 */
@GinModules(GinAppConfigurator.class)
public interface GinAppInjector extends Ginjector {
    ApplicationListener getApplicationListener();
}