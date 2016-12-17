package net.matthiasauer.libgdx.tictactoe.utils;

import com.badlogic.gdx.utils.ObjectSet;
import com.github.czyzby.kiwi.util.gdx.collection.GdxSets;

/**
 * Created by Matthias on 17/12/2016.
 */
public abstract class GdxObservable {
    private final ObjectSet<GdxObserver> observers = GdxSets.newSet();

    public void addObserver(GdxObserver gdxObserver) {
        this.observers.add(gdxObserver);
    }

    public void notifyObservers() {
        for (GdxObserver gdxObserver : this.observers) {
            gdxObserver.update();
        }
    }

    public void removeObserver(GdxObserver gdxObserver) {
        this.observers.remove(gdxObserver);
    }
}
