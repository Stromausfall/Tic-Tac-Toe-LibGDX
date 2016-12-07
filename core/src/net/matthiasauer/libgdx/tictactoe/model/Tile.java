package net.matthiasauer.libgdx.tictactoe.model;

import java.util.Observable;

/**
 * Created by Matthias on 07/12/2016.
 */
public class Tile extends Observable {
    private Owner owner = Owner.None;

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;

        // notify observers
        this.setChanged();
        this.notifyObservers();
    }
}
