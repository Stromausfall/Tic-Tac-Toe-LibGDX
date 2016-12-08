package net.matthiasauer.libgdx.tictactoe.model

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by Matthias on 08/12/2016.
 */
class GameStateServiceTest extends Specification {
    private Tile tileOwnedByPlayer = new Tile()
    private TileManager tileManager = Mock(TileManager)
    private GameState gameState = null


    @Shared
    private Tile tileOwnedByNone = new Tile()

    def setupSpec() {
        tileOwnedByNone.setOwner(Owner.None)
    }

    private Observer createObserver(GameStateService gameStateService) {
        return new Observer() {
                    @Override
                    void update(Observable o, Object arg) {
                        gameState = gameStateService.getGameState()
                    }
                }
    }

    @Unroll
    def "test that a horizontal win (line #lineNumber by player #player) notifies observers"() {
        given: "install a listener on the gameStateService"
            tileOwnedByPlayer.setOwner(player)
            GameStateServiceImpl gameStateService = new GameStateServiceImpl(tileManager)
            gameStateService.addObserver(createObserver(gameStateService))

            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    if (y == lineNumber) {
                        tileManager.get(x, y) >> tileOwnedByPlayer
                    } else {
                        tileManager.get(x, y) >> tileOwnedByNone
                    }
                }
            }
        when: "when a horizontal win is performed -  and the gameStateService is notified about the change"
            gameStateService.update(null, null)
        then:
            gameState.winner == player
        where:
            lineNumber || player
            0          || Owner.Circle
            0          || Owner.Cross
            1          || Owner.Circle
            1          || Owner.Cross
            2          || Owner.Circle
            2          || Owner.Cross
    }

    @Unroll
    def "test that a vertical win (column #lineNumber by player #player) notifies observers"() {
        given: "install a listener on the gameStateService"
            tileOwnedByPlayer.setOwner(player)
            GameStateServiceImpl gameStateService = new GameStateServiceImpl(tileManager)
            gameStateService.addObserver(createObserver(gameStateService))

            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    if (x == lineNumber) {
                        tileManager.get(x, y) >> tileOwnedByPlayer
                    } else {
                        tileManager.get(x, y) >> tileOwnedByNone
                    }
                }
            }
        when: "when a vertical win is performed -  and the gameStateService is notified about the change"
            gameStateService.update(null, null)
        then:
            gameState.winner == player
        where:
            lineNumber || player
            0          || Owner.Circle
            0          || Owner.Cross
            1          || Owner.Circle
            1          || Owner.Cross
            2          || Owner.Circle
            2          || Owner.Cross
    }

    @Unroll
    def "test that a diagonal win (variant 1 - player #player) notifies observers"() {
        given: "install a listener on the gameStateService"
            tileOwnedByPlayer.setOwner(player)
            GameStateServiceImpl gameStateService = new GameStateServiceImpl(tileManager)
            gameStateService.addObserver(createObserver(gameStateService))

            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    // catch
                    // X - -
                    // - X -
                    // - - X
                    if (x == y) {
                        tileManager.get(x, y) >> tileOwnedByPlayer
                    } else {
                        tileManager.get(x, y) >> tileOwnedByNone
                    }
                }
            }
        when: "when a vertical win is performed -  and the gameStateService is notified about the change"
            gameStateService.update(null, null)
        then:
            gameState.winner == player
        where:
            player << [Owner.Circle, Owner.Cross]
    }

    @Unroll
    def "test that a diagonal win (variant 2 - player #player) notifies observers"() {
        given: "install a listener on the gameStateService"
            tileOwnedByPlayer.setOwner(player)
            GameStateServiceImpl gameStateService = new GameStateServiceImpl(tileManager)
            gameStateService.addObserver(createObserver(gameStateService))

            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    // catch
                    // - - X
                    // - X -
                    // X - -
                    if ((x + y) == 2) {
                        tileManager.get(x, y) >> tileOwnedByPlayer
                    } else {
                        tileManager.get(x, y) >> tileOwnedByNone
                    }
                }
            }
        when: "when a vertical win is performed -  and the gameStateService is notified about the change"
            gameStateService.update(null, null)
        then:
            gameState.winner == player
        where:
            player << [Owner.Circle, Owner.Cross]
    }

    def "test that an undecided game termination is detected and the observer is notified"() {
        given:
            Tile cross = new Tile()
            Tile circle = new Tile()
            cross.setOwner(Owner.Cross)
            circle.setOwner(Owner.Circle)

            GameStateServiceImpl gameStateService = new GameStateServiceImpl(tileManager)
            gameStateService.addObserver(createObserver(gameStateService))

            // XXO
            tileManager.get(0, 0) >> cross
            tileManager.get(1, 0) >> cross
            tileManager.get(2, 0) >> circle
            // OOX
            tileManager.get(0, 1) >> circle
            tileManager.get(1, 1) >> circle
            tileManager.get(2, 1) >> cross
            // XXO
            tileManager.get(0, 2) >> cross
            tileManager.get(1, 2) >> cross
            tileManager.get(2, 2) >> circle

        when: "when a vertical win is performed -  and the gameStateService is notified about the change"
            gameStateService.update(null, null)
        then:
            gameState.winner == Owner.None
    }

    def "if the game is not terminated then the observer shouldn't be notified !"() {
        given:
            Tile cross = new Tile()
            Tile circle = new Tile()
            cross.setOwner(Owner.Cross)
            circle.setOwner(Owner.Circle)

            GameStateServiceImpl gameStateService = new GameStateServiceImpl(tileManager)
            gameStateService.addObserver(createObserver(gameStateService))

            // XXO
            tileManager.get(0, 0) >> cross
            tileManager.get(1, 0) >> cross
            tileManager.get(2, 0) >> circle
            // OOX
            tileManager.get(0, 1) >> circle
            tileManager.get(1, 1) >> circle
            tileManager.get(2, 1) >> cross
            // XX
            tileManager.get(0, 2) >> cross
            tileManager.get(1, 2) >> cross
            tileManager.get(2, 2) >> tileOwnedByNone

            gameState = null
        when:
            gameStateService.update(null, null)
        then:
            gameStateService.getGameState() == GameState.UNDECIDED
            gameState == null
    }
}
