package net.matthiasauer.libgdx.tictactoe.controller

import net.matthiasauer.libgdx.tictactoe.model.GameState
import net.matthiasauer.libgdx.tictactoe.model.GameStateService
import net.matthiasauer.libgdx.tictactoe.model.ModelManager
import spock.lang.Specification

/**
 * Created by Matthias on 08/12/2016.
 */
class ControllerManagerTest extends Specification {
    private GameStateService gameStateService

    def setup() {
        this.gameStateService = Mock(GameStateService)
        this.gameStateService.getGameState() >> GameState.UNDECIDED
    }

    def "test that the initialize method calls the initialize method on the ModelManager"() {
        given:
            Player player1 = Mock(Player)
            Player player2 = Mock(Player)
            ModelManager modelManager = Mock(ModelManager)
            ControllerManager controllerManager = new ControllerManagerImpl(modelManager, this.gameStateService)
        when:
            controllerManager.initialize(player1, player2)
        then:
            1 * modelManager.initialize()
    }

    def "test that the player's startTurn method is called after the initialize method"() {
        given:
            Player player1 = Mock(Player)
            Player player2 = Mock(Player)
            ModelManager modelManager = Mock(ModelManager)
            ControllerManager controllerManager = new ControllerManagerImpl(modelManager, this.gameStateService)
        when:
            controllerManager.initialize(player1, player2)
        then:
            1 * player1.startTurn()
            0 * player2.startTurn()
    }

    def "test that the tileClicked method is called if a tile has been clicked"() {
        given:
            Player player1 = Mock(Player)
            Player player2 = Mock(Player)
            ModelManager modelManager = Mock(ModelManager)
            ControllerManager controllerManager = new ControllerManagerImpl(modelManager, this.gameStateService)
            controllerManager.initialize(player1, player2)
        when:
            controllerManager.tileClicked(1, 2)
        then:
            1 * player1.tileClicked(1, 2)
            0 * player2.tileClicked(_ as Integer, _ as Integer)
    }

    def "test that the isTurnFinished() method is called directly after startTurn"() {
        given:
            Player player1 = Mock(Player)
            Player player2 = Mock(Player)
            ModelManager modelManager = Mock(ModelManager)
            ControllerManager controllerManager = new ControllerManagerImpl(modelManager, this.gameStateService)
        when:
            controllerManager.initialize(player1, player2)
        then:
            1 * player1.startTurn()
            1 * player1.isTurnFinished()
            0 * player2.startTurn()
    }

    def "test that if the isTurnFinished() method returns true - then the startTurn method of the next player is called"() {
        given:
            Player player1 = Mock(Player)
            Player player2 = Mock(Player)
            ModelManager modelManager = Mock(ModelManager)
            ControllerManager controllerManager = new ControllerManagerImpl(modelManager, this.gameStateService)
        when:
            controllerManager.initialize(player1, player2)
        then:
            2 * player1.startTurn()
            2 * player1.isTurnFinished() >>> [true, false]
            1 * player2.startTurn()
            1 * player2.isTurnFinished() >>> [true, false]
    }

    def "test that the isTurnFinished() method is called after the tileClicked"() {
        given:
            Player player1 = Mock(Player)
            Player player2 = Mock(Player)
            ModelManager modelManager = Mock(ModelManager)
            ControllerManager controllerManager = new ControllerManagerImpl(modelManager, this.gameStateService)
        when:
            controllerManager.initialize(player1, player2)
            controllerManager.tileClicked(1, 2)
        then:
            1 * player1.startTurn()
            1 * player1.tileClicked(_ as Integer, _ as Integer)
            2 * player1.isTurnFinished() >> false
    }

    def "test that after the game has terminated no new turn is started for any player"() {
        given: "start the game"
            Player player1 = Mock(Player)
            Player player2 = Mock(Player)
            ModelManager modelManager = Mock(ModelManager)
            ControllerManager controllerManager = new ControllerManagerImpl(modelManager, this.gameStateService)
        when: "before clicking the tile - the game state is now set to be terminated"
            controllerManager.initialize(player1, player2)
            controllerManager.tileClicked(1, 2)
        then: "first after initialize the turn is NOT finished, but after clicking turn is finished and the game state is terminated !"
            _ * gameStateService.getGameState() >>> [GameState.UNDECIDED, GameState.WON_BY_CIRCLE]
            1 * player1.startTurn()
            1 * player1.isTurnFinished() >> [false, true]
            0 * player2.startTurn()
    }
}
