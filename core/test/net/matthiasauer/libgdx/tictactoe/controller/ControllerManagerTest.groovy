package net.matthiasauer.libgdx.tictactoe.controller

import net.matthiasauer.libgdx.tictactoe.model.GameState
import net.matthiasauer.libgdx.tictactoe.model.GameStateService
import net.matthiasauer.libgdx.tictactoe.model.ModelManager
import net.matthiasauer.libgdx.tictactoe.model.TileManager
import net.matthiasauer.libgdx.tictactoe.view.ViewManager
import spock.lang.Specification

/**
 * Created by Matthias on 08/12/2016.
 */
class ControllerManagerTest extends Specification {
    private GameStateService gameStateService = Mock(GameStateService)
    private Player player1 = Mock(Player)
    private Player player2 = Mock(Player)
    private ModelManager modelManager = Mock(ModelManager)
    private TileManager tileManager = Mock(TileManager)
    private ViewManager viewManager = Mock(ViewManager)

    def setup() {
        this.gameStateService.getGameState() >> GameState.UNDECIDED
    }

    def "test that the initialize method calls the initialize method on the ModelManager"() {
        given:
            ControllerManager controllerManager = new ControllerManagerImpl(this.modelManager, this.gameStateService, this.tileManager, this.viewManager)
        when:
            controllerManager.initialize(player1, player2)
        then:
            1 * modelManager.initialize()
    }

    def "test that the initialize method calls the initialize method on the ViewManager"() {
        given:
            ControllerManager controllerManager = new ControllerManagerImpl(this.modelManager, this.gameStateService, this.tileManager, this.viewManager)
        when:
            controllerManager.initialize(player1, player2)
        then:
            1 * viewManager.initialize(controllerManager)
    }

    def "test that the initialize method calls the initialize method of the players"() {
        given:
            ControllerManager controllerManager = new ControllerManagerImpl(this.modelManager, this.gameStateService, this.tileManager, this.viewManager)
        when:
            controllerManager.initialize(player1, player2)
        then:
            1 * player1.initialize(_ as TileManager)
            1 * player2.initialize(_ as TileManager)
    }

    def "test that the player's startTurn method is called after the initialize method"() {
        given:
            ControllerManager controllerManager = new ControllerManagerImpl(this.modelManager, this.gameStateService, this.tileManager, this.viewManager)
        when:
            controllerManager.initialize(player1, player2)
        then:
            1 * player1.startTurn()
            0 * player2.startTurn()
    }

    def "test that the tileClicked method is called if a tile has been clicked"() {
        given:
            ControllerManager controllerManager = new ControllerManagerImpl(this.modelManager, this.gameStateService, this.tileManager, this.viewManager)
            controllerManager.initialize(player1, player2)
        when:
            controllerManager.tileClicked(1, 2)
        then:
            1 * player1.tileClicked(1, 2)
            0 * player2.tileClicked(_ as Integer, _ as Integer)
    }

    def "test that the isTurnFinished() method is called directly after startTurn"() {
        given:
            ControllerManager controllerManager = new ControllerManagerImpl(this.modelManager, this.gameStateService, this.tileManager, this.viewManager)
        when:
            controllerManager.initialize(player1, player2)
        then:
            1 * player1.startTurn()
            1 * player1.isTurnFinished()
            0 * player2.startTurn()
    }

    def "test that if the isTurnFinished() method returns true - then the startTurn method of the next player is called"() {
        given:
            ControllerManager controllerManager = new ControllerManagerImpl(this.modelManager, this.gameStateService, this.tileManager, this.viewManager)
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
            ControllerManager controllerManager = new ControllerManagerImpl(this.modelManager, this.gameStateService, this.tileManager, this.viewManager)
        when:
            controllerManager.initialize(player1, player2)
            controllerManager.tileClicked(1, 2)
        then:
            1 * player1.startTurn()
            1 * player1.tileClicked(_ as Integer, _ as Integer)
            2 * player1.isTurnFinished() >> false
    }

    def "test that the gameStateService is updated when we check if a player's turn begins"() {
        given:
            ControllerManager controllerManager = new ControllerManagerImpl(this.modelManager, this.gameStateService, this.tileManager, this.viewManager)
        when:
            controllerManager.initialize(player1, player2)
            controllerManager.tileClicked(1, 2)
        then:
            1 * player1.isTurnFinished() >> true
            2 * player2.isTurnFinished() >> false
            2 * this.gameStateService.update()
    }

    def "test that after the game has terminated no new turn is started for any player"() {
        given: "start the game"
            ControllerManager controllerManager = new ControllerManagerImpl(this.modelManager, this.gameStateService, this.tileManager, this.viewManager)
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
