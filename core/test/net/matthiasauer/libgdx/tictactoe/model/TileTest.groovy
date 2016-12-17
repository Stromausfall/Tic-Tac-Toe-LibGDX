package net.matthiasauer.libgdx.tictactoe.model

import net.matthiasauer.libgdx.tictactoe.utils.GdxObserver
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by Matthias on 07/12/2016.
 */
class TileTest extends Specification {
    def "test that initially the tile is now owned by any player"() {
        given:
            Tile tile = new Tile()
        expect:
            tile.getOwner() == Owner.None
    }

    @Unroll
    def "test that changes to the owner (to #owner) of the tile notifies observers"() {
        given: "a tile on which an observer has been installed"
            Tile tile = new Tile()
            int counter = 0
            tile.addObserver(new GdxObserver() {
                @Override
                void update() {
                    counter++
                }
            })
        when: "on a change to the owner"
            tile.setOwner(owner)
        then: "the owner is changed and the observer has been notifed"
            tile.getOwner() == owner
            counter == 1
        where:
            owner << [Owner.Cross, Owner.Circle]
    }
}
