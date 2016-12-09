package net.matthiasauer.libgdx.tictactoe.model

import spock.lang.Specification

/**
 * Created by Matthias on 08/12/2016.
 */
class ModelManagerTest extends Specification {
    def "test that the ModelManager initialize method calls initialize on the TileManager and the GameStateService"() {
        given:
            GameStateService gameStateService = Mock(GameStateService)
            TileManager tileManager = Mock(TileManager)
            ModelManager modelManager = new ModelManagerImpl(gameStateService, tileManager)
        when:
            modelManager.initialize()
        then:
            1 * gameStateService.initialize()
            1 * tileManager.initialize()
    }
}
