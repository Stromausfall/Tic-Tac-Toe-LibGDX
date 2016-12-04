package net.matthiasauer.libgdx.tictactoe

import spock.lang.Specification


/**
 * Created by Matthias on 04/12/2016.
 */
class FooTest extends Specification {
    def "foo testing"() {
        given:
            Foo foo = new FooImplementation()
        when:
            String result = foo.getAssetName()
        then:
            result == "asdfasdf"
    }
}