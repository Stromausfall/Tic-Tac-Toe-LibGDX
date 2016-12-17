package net.matthiasauer.libgdx.tictactoe.controller

import net.matthiasauer.libgdx.tictactoe.model.Owner
import net.matthiasauer.libgdx.tictactoe.model.Tile
import net.matthiasauer.libgdx.tictactoe.model.TileManager
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

/**
 * Created by Matthias on 10/12/2016.
 */
class HumanPlayerTest extends Specification {

    @Subject
    private Player classUnderTest
    private TileManager tileManager
    private Tile tile

    def setup() {
        this.tile = Mock(Tile)
        this.tileManager = Mock(TileManager)
        this.classUnderTest = new HumanPlayer(Owner.Circle)
        this.classUnderTest.initialize(this.tileManager)
    }

    def "test that after starting a turn the isTurnFinished() method returns false"() {
        when:
            classUnderTest.startTurn()
        then:
            classUnderTest.isTurnFinished() == false
    }

    @Unroll
    def "test that if the tileClickedMethod has been clicked on an already taken tile - the turn finished method returns true"() {
        given:
            classUnderTest.startTurn()
        when:
            classUnderTest.tileClicked(2, 1)
        then:
            !classUnderTest.isTurnFinished()
            1 * tileManager.get(2, 1) >> tile
            1 * tile.getOwner() >> player
        where:
            player << [Owner.Circle, Owner.Cross]
    }

    def "test that if the tileClickedMethod has been clicked on a free tile - the turn finished method returns true"() {
        given:
            classUnderTest.startTurn()
        when:
            classUnderTest.tileClicked(2, 1)
        then:
            classUnderTest.isTurnFinished()
            1 * tileManager.get(2, 1) >> tile
            1 * tile.getOwner() >> Owner.None
    }

    def "test that if the startTurn method resets the isTurnFinished value"() {
        given:
            classUnderTest.startTurn()
        when: "first clicking on a free tile and then start a new turn"
            classUnderTest.tileClicked(2, 1)
            classUnderTest.startTurn()
        then:
            !classUnderTest.isTurnFinished()
            1 * tileManager.get(2, 1) >> tile
            1 * tile.getOwner() >> Owner.None
    }

    def "test that if the tileClickedMethod has been clicked on a free tile - then the tile owner is changed to the player"() {
        given:
            classUnderTest = new HumanPlayer(owner)
            classUnderTest.initialize(this.tileManager)
            classUnderTest.startTurn()
        when:
            classUnderTest.tileClicked(2, 1)
        then:
            1 * tileManager.get(2, 1) >> tile
            1 * tile.getOwner() >> Owner.None   // first return that the tile has no owner
            1 * tile.setOwner(owner)            // then expect an owner
        where:
            owner << [Owner.Cross, Owner.Circle]
    }
}
