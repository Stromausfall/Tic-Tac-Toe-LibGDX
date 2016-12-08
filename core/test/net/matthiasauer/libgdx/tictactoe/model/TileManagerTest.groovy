package net.matthiasauer.libgdx.tictactoe.model

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by Matthias on 08/12/2016.
 */
class TileManagerTest extends Specification {
    @Unroll
    def "test that the initialize method creates tiles x:#x, y:#y has no tile=#tileIsNull"() {
        given:
            TileManager tileManager = new TileManagerImpl()
        when:
            tileManager.initialize()
        then: "check whether the tile for the coordinates is null"
            tileIsNull == (tileManager.get(x, y) == null)
        where:
            x  | y  | tileIsNull
            -1 | -1 | true
            0  | -1 | true
            -1 | 0  | true
            0  | 0  | false
            0  | 1  | false
            0  | 2  | false
            1  | 0  | false
            1  | 1  | false
            1  | 2  | false
            2  | 0  | false
            2  | 1  | false
            2  | 2  | false
            3  | 0  | true
            0  | 3  | true
            3  | 3  | true
    }

    def "test that the same coordinates always return the same tile"() {
        given:
            TileManager tileManager = new TileManagerImpl()
            tileManager.initialize()
        when:
            Tile tile1 = tileManager.get(2, 2)
            Tile tile2 = tileManager.get(2, 2)
        then:
            tile1 == tile2
    }

    def "test that the initialize method creates new tiles"() {
        given:
            TileManager tileManager = new TileManagerImpl()
            tileManager.initialize()
            Tile tile1 = tileManager.get(2, 2)
        when:
            tileManager.initialize()
            Tile tile2 = tileManager.get(2, 2)
        then:
            tile1 != tile2
    }

    @Unroll
    def "test that the initial owner of the tile (#x/#y) is Owner.None"() {
        given:
            TileManager tileManager = new TileManagerImpl()
        when:
            tileManager.initialize()
        then:
            tileManager.get(x, y).owner == Owner.None
        where:
            x | y
            0 | 0
            1 | 0
            2 | 0
            0 | 1
            1 | 1
            2 | 1
            0 | 2
            1 | 2
            2 | 2
    }
}
