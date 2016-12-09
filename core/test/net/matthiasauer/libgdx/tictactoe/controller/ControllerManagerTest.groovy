package net.matthiasauer.libgdx.tictactoe.controller

import net.matthiasauer.libgdx.tictactoe.model.ModelManager;
import spock.lang.Specification;

/**
 * Created by Matthias on 08/12/2016.
 */
class ControllerManagerTest extends Specification {
    def "test that the initialize method calls the initialize method on the ModelManager"() {
        given:
            Player player1 = Mock(Player)
            Player player2 = Mock(Player)
            ModelManager modelManager = Mock(ModelManager)
            ControllerManager controllerManager = new ControllerManagerImpl(modelManager)
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
            ControllerManager controllerManager = new ControllerManagerImpl(modelManager)
        when:
            controllerManager.initialize(player1, player2)
        then:
            1 * player1.startTurn()
            0 * player2.startTurn()
    }
}
