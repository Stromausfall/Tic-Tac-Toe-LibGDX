package net.matthiasauer.libgdx.tictactoe.controller

import net.matthiasauer.libgdx.tictactoe.model.Owner
import net.matthiasauer.libgdx.tictactoe.model.Tile
import net.matthiasauer.libgdx.tictactoe.model.TileManager
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

/**
 * Created by Matthias on 11/12/2016.
 */
class ComputerPlayerTest extends Specification {

    @Subject
    private Player classUnderTest
    private TileManager tileManager
    private Tile tile

    def setup() {
        this.tile = Mock(Tile)
        this.tileManager = Mock(TileManager)
        this.classUnderTest = new ComputerPlayer(this.tileManager, Owner.Circle)
    }

    def "test that after the startTurn() method is called the turn is finished for the computer player"() {
        when:
            classUnderTest.startTurn()
        then:
            classUnderTest.isTurnFinished()
            _ * tileManager.get(_ as Integer, _ as Integer) >> tile
            _ * tile.setOwner(Owner.None)
            _ * tile.getOwner() >> Owner.None
    }

    def "test that calling the tileClicked method has no noticeable effect"() {
        when:
            classUnderTest.tileClicked(2, 1)
        then:
            classUnderTest.isTurnFinished()
            0 * tileManager.get(_ as Integer, _ as Integer)
    }

    @Unroll
    def "test that the owner of the retrieved free tile is changed to the player"() {
        when:
            classUnderTest = new ComputerPlayer(this.tileManager, player)
            classUnderTest.startTurn()
        then:
            classUnderTest.isTurnFinished()
            _ * tile.getOwner() >> Owner.None
            1 * tileManager.get(_ as Integer, _ as Integer) >> tile
            1 * tile.setOwner(player)
        where:
            player << [Owner.Circle, Owner.Cross]
    }

    @Unroll
    def "test that tiles are searched until a free one is found"() {
        when:
            classUnderTest = new ComputerPlayer(this.tileManager, player)
            classUnderTest.startTurn()
        then:
            classUnderTest.isTurnFinished()
            _ * tile.getOwner() >>> [Owner.Circle, Owner.Circle, Owner.Cross, Owner.None]
            4 * tileManager.get(_ as Integer, _ as Integer) >> tile
            1 * tile.setOwner(player)
        where:
            player << [Owner.Circle, Owner.Cross]
    }
}
